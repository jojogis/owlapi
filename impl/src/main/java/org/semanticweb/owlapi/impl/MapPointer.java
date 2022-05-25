/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.impl;

import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.empty;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.impl.InitVisitorFactory.InitCollectionVisitor;
import org.semanticweb.owlapi.impl.InitVisitorFactory.InitVisitor;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.utilities.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.utility.CollectionFactory;
import org.semanticweb.owlapi.utility.SmallSet;

import com.carrotsearch.hppcrt.cursors.ObjectCursor;
import com.carrotsearch.hppcrt.maps.ObjectObjectHashMap;
import com.carrotsearch.hppcrt.procedures.ObjectProcedure;
import com.carrotsearch.hppcrt.sets.ObjectHashSet;

/**
 * * Objects that identify contained maps - so that getting the keys of a specific map does not
 * require a specific method for each map nor does it require the map to be copied and returned.
 * 
 * @author ignazio
 * @param <K> key
 * @param <V> value
 */
public class MapPointer<K, V extends OWLAxiom> {

    @Nullable
    private final AxiomType<?> type;
    @Nullable
    private final OWLAxiomVisitorEx<?> visitor;
    private boolean initialized;
    protected final Internals i;
    @Nullable
    private SoftReference<Set<IRI>> iris;
    private int size = 0;
    private final ObjectObjectHashMap<K, Collection<V>> map = new ObjectObjectHashMap<>(17, 0.75F);
    private boolean neverTrimmed = true;
    private final Class<V> valueWithness;

    /**
     * @param t type of axioms contained
     * @param v visitor
     * @param initialized true if initialized
     * @param i internals containing this pointer
     * @param valueWithness witness for the value type
     */
    public MapPointer(@Nullable AxiomType<?> t, @Nullable OWLAxiomVisitorEx<?> v,
        boolean initialized, Internals i, Class<V> valueWithness) {
        type = t;
        visitor = v;
        this.initialized = initialized;
        this.i = checkNotNull(i, "i cannot be null");
        this.valueWithness = valueWithness;
    }

    /**
     * This method replicates the Map.forEach on all the key/value pairs
     * 
     * @param consumer a consumer with two arguments
     */
    public void forEach(BiConsumer<K, V> consumer) {
        keySet().forEach(k -> forEach(k, v -> consumer.accept(k, v)));
    }

    /**
     * @param e entity
     * @return true if an entity with the same iri as the input exists in the collection
     */
    public synchronized boolean containsReference(K e) {
        return map.containsKey(e);
    }

    /**
     * @param e IRI
     * @return true if an entity with the same iri as the input exists in the collection
     */
    public synchronized boolean containsReference(IRI e) {
        Set<IRI> set = null;
        if (iris != null) {
            set = iris.get();
        }
        if (set == null) {
            set = initSet();
        }
        return set.contains(e);
    }

    private Set<IRI> initSet() {
        Set<IRI> set = CollectionFactory.createSet();
        ObjectProcedure<K> consumer = k -> consumer(set, k);
        map.keys().forEach(consumer);
        iris = new SoftReference<>(set);
        return set;
    }

    protected void consumer(Set<IRI> set, K k) {
        if (k instanceof HasIRI) {
            set.add(((HasIRI) k).getIRI());
        } else if (k instanceof IRI) {
            set.add((IRI) k);
        }
    }

    /**
     * @return true if initialized
     */
    public synchronized boolean isInitialized() {
        return initialized;
    }

    /**
     * init the map pointer
     * 
     * @return the map pointer
     */
    @SuppressWarnings({"unchecked"})
    public synchronized MapPointer<K, V> init() {
        if (initialized) {
            return this;
        }
        initialized = true;
        if (visitor == null || type == null) {
            return this;
        }
        AxiomType<?> t = type;
        assert t != null;
        if (visitor instanceof InitVisitor) {
            InitVisitor<K> v = (InitVisitor<K>) visitor;
            i.getAxiomsByType().forEach(t, ax -> putInternal(ax.accept(v), (V) ax));
        } else if (visitor instanceof InitCollectionVisitor) {
            InitCollectionVisitor<K> v = (InitCollectionVisitor<K>) visitor;
            i.getAxiomsByType().forEach(t,
                ax -> ax.accept(v).forEach(key -> putInternal(key, (V) ax)));
        }
        return this;
    }

    @Override
    public synchronized String toString() {
        return initialized + map.toString();
    }

    /**
     * @return keyset
     */
    public synchronized Stream<K> keySet() {
        init();
        return streamCursor(map.keys().spliterator());
    }

    private static <Q> Stream<Q> streamCursor(Spliterator<ObjectCursor<Q>> i) {
        return StreamSupport.stream(i, false).map(v -> v.value);
    }

    private static <Q> Stream<Q> stream(Spliterator<Q> i) {
        return StreamSupport.stream(i, false);
    }

    /**
     * @param key key to look up
     * @return value
     */
    public synchronized Stream<V> getValues(K key) {
        init();
        Collection<V> t = map.get(key);
        if (t == null) {
            return Stream.empty();
        }
        return stream(t.spliterator());
    }

    /**
     * @param key key to look up
     * @param function consumer to apply
     */
    public synchronized void forEach(K key, Consumer<V> function) {
        init();
        get(key).forEach(function);
    }

    /**
     * @param key key to look up
     * @param function predicate to evaluate
     * @return value
     */
    public synchronized boolean matchOnValues(K key, Predicate<V> function) {
        init();
        return get(key).anyMatch(function);
    }

    /**
     * @param key key to look up
     * @return value
     */
    public synchronized Collection<V> getValuesAsCollection(K key) {
        init();
        Collection<V> t = map.get(key);
        if (t == null) {
            return Collections.emptyList();
        }
        return asUnorderedSet(stream(t.spliterator()));
    }

    /**
     * @param key key to look up
     * @return value
     */
    public synchronized int countValues(K key) {
        init();
        return count(key);
    }

    private int count(K k) {
        Collection<V> t = map.get(k);
        if (t == null) {
            return 0;
        }
        return t.size();
    }

    /**
     * @param <O> output type
     * @param key key to look up
     * @param classType type of the returned values
     * @return value
     */
    @SuppressWarnings("unchecked")
    public synchronized <O extends V> Stream<O> values(K key,
        @SuppressWarnings("unused") Class<O> classType) {
        init();
        Collection<V> t = map.get(key);
        if (t == null) {
            return empty();
        }
        return (Stream<O>) stream(t.spliterator());
    }

    /**
     * @param <T> type of key
     * @param filter filter to satisfy
     * @param key key
     * @return set of values
     */
    public synchronized <T> Collection<OWLAxiom> filterAxioms(OWLAxiomSearchFilter filter, T key) {
        init();
        List<OWLAxiom> toReturn = new ArrayList<>();
        for (AxiomType<?> at : filter.getAxiomTypes()) {
            // This method is only used for MapPointer<AxiomType, OWLAxiom>
            @SuppressWarnings("unchecked")
            Collection<V> collection = map.get((K) at);
            if (collection != null) {
                stream(collection.spliterator()).filter(x -> filter.pass(x, key))
                    .forEach(toReturn::add);
            }
        }
        return toReturn;
    }

    /**
     * @param key key to add
     * @param value value to add
     * @return true if addition happens
     */
    public synchronized boolean put(K key, V value) {
        // lazy init: no elements added until a recall is made
        if (!initialized) {
            return false;
        }
        iris = null;
        return putInternal(key, value);
    }

    /**
     * @param key key to look up
     * @param value value to remove
     * @return true if removal happens
     */
    public synchronized boolean remove(K key, V value) {
        if (!initialized) {
            return false;
        }
        iris = null;
        return removeInternal(key, value);
    }

    /**
     * @param key key to look up
     * @return true if there are values for key
     */
    public synchronized boolean containsKey(K key) {
        init();
        return map.containsKey(key);
    }

    /**
     * @param key key to look up
     * @param value value to look up
     * @return true if key and value are contained
     */
    public synchronized boolean contains(K key, V value) {
        init();
        return containsEntry(key, value);
    }

    /**
     * @return all values contained
     */
    public synchronized Stream<V> getAllValues() {
        init();
        return values();
    }

    /**
     * @return number of mapping contained
     */
    public synchronized int size() {
        init();
        if (neverTrimmed) {
            // trimToSize();
        }
        return size;
    }

    /**
     * @return true if empty
     */
    public synchronized boolean isEmpty() {
        init();
        return size == 0;
    }

    private boolean putInternal(@Nullable K k, V v) {
        if (k == null) {
            return false;
        }
        Collection<V> set = map.get(k);
        if (set == null) {
            set = Collections.singleton(v);
            map.put(k, set);
            size++;
            return true;
        }
        if (set.size() == 1) {
            if (set.contains(v)) {
                return false;
            } else {
                set = new SmallSet<>(set);
                map.put(k, set);
            }
        } else if (set.size() == 3) {
            if (set.contains(v)) {
                return false;
            } else {
                set = new HPPCSet<>(set, v, valueWithness);
                map.put(k, set);
                size++;
                return true;
            }
        }
        boolean added = set.add(v);
        if (added) {
            size++;
        }
        return added;
    }

    private boolean containsEntry(K k, V v) {
        Collection<V> t = map.get(k);
        if (t == null) {
            return false;
        }
        return t.contains(v);
    }

    private boolean removeInternal(K k, V v) {
        if (neverTrimmed) {
            // trimToSize();
        }
        Collection<V> t = map.get(k);
        if (t == null) {
            return false;
        }
        if (t.size() == 1) {
            if (t.contains(v)) {
                map.remove(k);
                size--;
                return true;
            } else {
                return false;
            }
        }
        boolean removed = t.remove(v);
        if (removed) {
            size--;
        }
        if (t.isEmpty()) {
            map.remove(k);
        }
        return removed;
    }

    private Stream<V> values() {
        return stream(map.values().spliterator()).flatMap(s -> s.value.stream());
    }

    private Stream<V> get(K k) {
        Collection<V> t = map.get(k);
        if (t == null) {
            return Stream.empty();
        }
        return stream(t.spliterator());
    }
}


class HPPCSet<S> implements Collection<S> {
    private ObjectHashSet<S> delegate;
    private final Class<S> witness;

    public HPPCSet(Class<S> c) {
        delegate = new ObjectHashSet<>();
        this.witness = c;
    }

    public HPPCSet(int initialCapacity, Class<S> c) {
        delegate = new ObjectHashSet<>(initialCapacity);
        this.witness = c;
    }

    public HPPCSet(int initialCapacity, double loadFactor, Class<S> c) {
        delegate = new ObjectHashSet<>(initialCapacity, loadFactor);
        this.witness = c;
    }

    public HPPCSet(Collection<S> container, Class<S> c) {
        delegate = new ObjectHashSet<>(container.size() + 1);
        this.witness = c;
        addAll(container);
    }

    public HPPCSet(Collection<S> container, S s, Class<S> c) {
        delegate = new ObjectHashSet<>(container.size() + 1);
        this.witness = c;
        addAll(container);
        add(s);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return witness.isInstance(o) && delegate.contains(witness.cast(o));
    }

    @Override
    public Iterator<S> iterator() {
        final ObjectHashSet<S>.EntryIterator iterator = delegate.iterator();
        return new Iterator<S>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public S next() {
                return iterator.next().value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(@Nullable T[] a) {
        throw new UnsupportedOperationException("Not suppoerted for " + getClass());
    }

    @Override
    public boolean add(@Nullable S e) {
        return delegate.add(e);
    }

    @Override
    public boolean remove(@Nullable Object o) {
        if (witness.isInstance(o)) {
            return delegate.remove(witness.cast(o));
        }
        return false;
    }

    @Override
    public boolean containsAll(@Nullable Collection<?> c) {
        if (c == null) {
            return false;
        }
        for (Object o : c) {
            if (!witness.isInstance(o) || !delegate.contains(witness.cast(o))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@Nullable Collection<? extends S> c) {
        if (c == null) {
            return false;
        }
        boolean toReturn = false;
        for (S s : c) {
            if (add(s)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    @Override
    public boolean removeAll(@Nullable Collection<?> c) {
        if (c == null) {
            return false;
        }
        boolean toReturn = false;
        for (Object s : c) {
            if (remove(s)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    @Override
    public boolean retainAll(@Nullable Collection<?> c) {
        if (c == null) {
            return false;
        }
        List<S> asList = asList(c.stream().filter(witness::isInstance), witness);
        return delegate.retainAll(new HPPCSet<>(asList, witness).delegate) > 0;
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }
}