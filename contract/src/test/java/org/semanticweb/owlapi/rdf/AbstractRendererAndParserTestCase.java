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
package org.semanticweb.owlapi.rdf;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLOntologyStorer;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyBuilderImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public abstract class AbstractRendererAndParserTestCase extends TestBase {

    @SuppressWarnings("null")
    @Nonnull
    private OWLOntologyManager man;

    @Before
    public void setUp() {
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLOntologyBuilderImpl builder = new OWLOntologyBuilderImpl();
        man.getOntologyFactories().add(
                new EmptyInMemOWLOntologyFactory(builder),
                new ParsableOWLOntologyFactory(builder));
        man.getOntologyStorers().add(new RDFXMLOntologyStorer());
        man.getOntologyParsers().add(new RDFXMLParser());
    }

    @Nonnull
    public OWLClass createClass() {
        return man.getOWLDataFactory().getOWLClass(TestUtils.createIRI());
    }

    @Nonnull
    public OWLAnnotationProperty createAnnotationProperty() {
        return getManager().getOWLDataFactory().getOWLAnnotationProperty(
                TestUtils.createIRI());
    }

    @Nonnull
    public OWLObjectProperty createObjectProperty() {
        return man.getOWLDataFactory().getOWLObjectProperty(
                TestUtils.createIRI());
    }

    @Nonnull
    public OWLDataProperty createDataProperty() {
        return man.getOWLDataFactory()
                .getOWLDataProperty(TestUtils.createIRI());
    }

    @Nonnull
    public OWLIndividual createIndividual() {
        return man.getOWLDataFactory().getOWLNamedIndividual(
                TestUtils.createIRI());
    }

    @Nonnull
    public OWLOntologyManager getManager() {
        return man;
    }

    @Nonnull
    protected OWLDataFactory getDataFactory() {
        return man.getOWLDataFactory();
    }

    @SuppressWarnings("null")
    @Test
    public void testSaveAndReload() throws OWLOntologyCreationException,
            OWLOntologyStorageException, IOException {
        OWLOntology ontA = man.createOntology(IRI
                .create("http://rdfxmltests/ontology"));
        for (OWLAxiom ax : getAxioms()) {
            man.applyChange(new AddAxiom(ontA, ax));
        }
        // OWLOntologyAnnotationAxiom anno =
        // getDataFactory().getOWLOntologyAnnotationAxiom(ontA,
        // getDataFactory().getCommentAnnotation(getClassExpression()));
        // man.applyChange(new AddAxiom(ontA, anno));
        File tempFile = folder.newFile("Ontology.owlapi");
        man.saveOntology(ontA, IRI.create(tempFile.toURI()));
        man.removeOntology(ontA);
        OWLOntology ontB = man.loadOntologyFromOntologyDocument(IRI
                .create(tempFile.toURI()));
        Set<OWLLogicalAxiom> AminusB = ontA.getLogicalAxioms();
        AminusB.removeAll(ontB.getAxioms());
        Set<OWLLogicalAxiom> BminusA = ontB.getLogicalAxioms();
        BminusA.removeAll(ontA.getAxioms());
        StringBuilder msg = new StringBuilder();
        if (AminusB.isEmpty() && BminusA.isEmpty()) {
            msg.append("Ontology save/load roundtripp OK.\n");
        } else {
            msg.append("Ontology save/load roundtripping error.\n");
            msg.append("=> " + AminusB.size()
                    + " axioms lost in roundtripping.\n");
            for (OWLAxiom axiom : AminusB) {
                msg.append(axiom.toString() + "\n");
            }
            msg.append("=> " + BminusA.size()
                    + " axioms added after roundtripping.\n");
            for (OWLAxiom axiom : BminusA) {
                msg.append(axiom.toString() + "\n");
            }
        }
        assertTrue(msg.toString(), AminusB.isEmpty() && BminusA.isEmpty());
    }

    protected abstract Set<OWLAxiom> getAxioms();

    protected abstract String getClassExpression();
}