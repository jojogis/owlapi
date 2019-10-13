package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.Translators.ListItemTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates an rdf:List into a Java {@code List}, or Java {@code Set}. The type of list (i.e. the
 * type of objects in the list) are determined by a {@code ListItemTranslator}. The translator
 * consumes all triples which are used in the translation.
 *
 * @param <O> type
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
class OptimisedListTranslator<O extends OWLObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptimisedListTranslator.class);
    private final OWLRDFConsumer consumer;
    private final ListItemTranslator<O> translator;

    /**
     * @param consumer consumer
     * @param translator translator
     */
    protected OptimisedListTranslator(OWLRDFConsumer consumer, ListItemTranslator<O> translator) {
        this.consumer = consumer;
        this.translator = translator;
    }

    protected OWLRDFConsumer getConsumer() {
        return consumer;
    }

    private void translateList(IRI mainNode, List<O> list) {
        IRI current = mainNode;
        while (current != null) {
            IRI firstResource = consumer.lists.getFirstResource(current, true);
            if (firstResource != null) {
                O translate = translator.translate(firstResource);
                if (translate != null) {
                    LOGGER.debug("list: {}", translate);
                    list.add(translate);
                } else {
                    LOGGER.warn("Possible malformed list: cannot translate it {}", firstResource);
                }
            } else {
                OWLLiteral literal = consumer.lists.getFirstLiteral(current);
                if (literal != null) {
                    O translate = translator.translate(literal);
                    if (translate != null) {
                        list.add(translate);
                    }
                } else {
                    // Empty list?
                    LOGGER.warn("Possible malformed list: rdf:first triple missing");
                }
            }
            current = consumer.lists.getRest(current, true);
        }
    }

    /**
     * @param mainNode mainNode
     * @return translated list
     */
    @SuppressWarnings("unchecked")
    public List<O> translateList(IRI mainNode) {
        boolean shared = consumer.anon.isAnonymousSharedNode(mainNode.toString());
        List<O> list;
        if (shared) {
            Object o = consumer.getSharedAnonymousNode(mainNode);
            if (o instanceof List) {
                list = (List<O>) o;
            } else {
                list = new ArrayList<>();
                translateList(mainNode, list);
                consumer.addSharedAnonymousNode(mainNode, list);
            }
        } else {
            list = new ArrayList<>();
            translateList(mainNode, list);
        }
        return list;
    }
}