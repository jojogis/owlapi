package org.semanticweb.owlapi.apitest.fileroundtrip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataAllValuesFrom;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataHasValue;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataOneOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataSomeValuesFrom;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DatatypeRestriction;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.FacetRestriction;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Float;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.HasKey;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Integer;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectAllValuesFrom;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectExactCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectHasSelf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectHasValue;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectMaxCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectMinCardinality;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectOneOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectUnionOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi.apitest.TestEntities.A;
import static org.semanticweb.owlapi.apitest.TestEntities.B;
import static org.semanticweb.owlapi.apitest.TestEntities.C;
import static org.semanticweb.owlapi.apitest.TestEntities.DP;
import static org.semanticweb.owlapi.apitest.TestEntities.P;
import static org.semanticweb.owlapi.apitest.TestEntities.Q;
import static org.semanticweb.owlapi.apitest.TestEntities.R;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.search.Searcher;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class FileRoundTripCorrectAxiomsTestCase extends TestBase {

    private static final String OWLAPI_TEST = "http://www.semanticweb.org/owlapi/test#";
    private static final OWLDatatype DATAB = Datatype(iri("B"));

    protected void assertEqualsSet(String ontology, OWLAxiom... axioms) {
        assertEquals(asUnorderedSet(ontologyFromClasspathFile(ontology).axioms()),
            new HashSet<>(Arrays.asList(axioms)));
    }

    @Test
    public void testCorrectAxiomAnnotatedPropertyAssertions() {
        OWLOntology ontology = ontologyFromClasspathFile("AnnotatedPropertyAssertions.rdf");
        OWLNamedIndividual subject = NamedIndividual(IRI("http://Example.com#", "myBuilding"));
        OWLObjectProperty predicate = ObjectProperty(IRI("http://Example.com#", "located_at"));
        OWLNamedIndividual object = NamedIndividual(IRI("http://Example.com#", "myLocation"));
        OWLAxiom ax = ObjectPropertyAssertion(predicate, subject, object);
        assertTrue(ontology.containsAxiom(ax, EXCLUDED, AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS));
        Set<OWLAxiom> axioms = asUnorderedSet(ontology.axiomsIgnoreAnnotations(ax, EXCLUDED));
        assertEquals(1, axioms.size());
        OWLAxiom theAxiom = axioms.iterator().next();
        assertTrue(theAxiom.isAnnotated());
    }

    @Test
    public void testContainsComplexSubPropertyAxiom() {
        List<OWLObjectProperty> chain = Arrays.asList(P, Q);
        assertEqualsSet("ComplexSubProperty.rdf", df.getOWLSubPropertyChainOfAxiom(chain, R));
    }

    @Test
    public void testCorrectAxiomsDataAllValuesFrom() {
        assertEqualsSet("DataAllValuesFrom.rdf", SubClassOf(A, DataAllValuesFrom(DP, DATAB)),
            Declaration(DATAB), Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataComplementOf() {
        OWLDataRange complement = DataComplementOf(Integer());
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, complement);
        assertEqualsSet("DataComplementOf.rdf", ax, Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataHasValue() {
        assertEqualsSet("DataHasValue.rdf", SubClassOf(A, DataHasValue(DP, Literal(3))),
            Declaration(DP), SubClassOf(A, DataHasValue(DP, Literal("A", ""))));
    }

    @Test
    public void testCorrectAxiomsDataIntersectionOf() {
        OWLDataRange intersection = DataIntersectionOf(Integer(), Float());
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, intersection);
        assertEqualsSet("DataIntersectionOf.rdf", ax, Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataMaxCardinality() {
        assertEqualsSet("DataMaxCardinality.rdf",
            SubClassOf(A, DataMaxCardinality(3, DP, TopDatatype())), Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataMinCardinality() {
        assertEqualsSet("DataMinCardinality.rdf",
            SubClassOf(A, DataMinCardinality(3, DP, TopDatatype())), Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataOneOf() {
        OWLDataRange oneOf = DataOneOf(Literal(30), Literal(31f));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, oneOf);
        assertEqualsSet("DataOneOf.rdf", ax, Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataSomeValuesFrom() {
        assertEqualsSet("DataSomeValuesFrom.rdf", SubClassOf(A, DataSomeValuesFrom(DP, DATAB)),
            Declaration(DATAB), Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDataUnionOf() {
        OWLDataRange union = DataUnionOf(Integer(), Float());
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, union);
        assertEqualsSet("DataUnionOf.rdf", ax, Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDatatypeRestriction() {
        OWLDataRange dr =
            DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.MIN_INCLUSIVE, Literal(18)),
                FacetRestriction(OWLFacet.MAX_INCLUSIVE, Literal(30)));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, dr);
        assertEqualsSet("DatatypeRestriction.rdf", ax, Declaration(DP));
    }

    @Test
    public void testCorrectAxiomsDeclarations() {
        OWLClass c = Class(IRI("http://www.semanticweb.org/ontologies/declarations#", "Cls"));
        OWLObjectProperty o =
            ObjectProperty(IRI("http://www.semanticweb.org/ontologies/declarations#", "op"));
        OWLDataProperty d =
            DataProperty(IRI("http://www.semanticweb.org/ontologies/declarations#", "dp"));
        OWLNamedIndividual i =
            NamedIndividual(IRI("http://www.semanticweb.org/ontologies/declarations#", "ni"));
        OWLAnnotationProperty ap =
            AnnotationProperty(IRI("http://www.semanticweb.org/ontologies/declarations#", "ap"));
        OWLDatatype datatype =
            Datatype(IRI("http://www.semanticweb.org/ontologies/declarations#", "dt"));
        assertEqualsSet("TestDeclarations.rdf", Declaration(c), Declaration(o), Declaration(d),
            Declaration(i), Declaration(ap), Declaration(datatype));
    }

    @Test
    public void testDeprecatedAnnotationAssertionsPresent() {
        OWLOntology ont = ontologyFromClasspathFile("Deprecated.rdf");
        OWLClass cls = Class(IRI(OWLAPI_TEST, "ClsA"));
        Searcher.annotationObjects(ont.annotationAssertionAxioms(cls.getIRI(), INCLUDED))
            .forEach(a -> a.isDeprecatedIRIAnnotation());
        OWLDataProperty prop = DataProperty(IRI(OWLAPI_TEST, "prop"));
        Searcher.annotationObjects(ont.annotationAssertionAxioms(prop.getIRI(), INCLUDED))
            .forEach(a -> assertTrue(a.isDeprecatedIRIAnnotation()));
    }

    @Test
    public void testContainsDisjointClasses() {
        assertEqualsSet("DisjointClasses.rdf", DisjointClasses(A, B, C));
    }

    @Test
    public void testCorrectAxiomsHasKey() {
        OWLClass cls = Class(IRI("http://example.com/", "Person"));
        OWLDataProperty propP = DataProperty(IRI("http://example.com/", "dataProperty"));
        OWLObjectProperty propQ = ObjectProperty(IRI("http://example.com/", "objectProperty"));
        assertEqualsSet("HasKey.rdf", HasKey(cls, propQ, propP), Declaration(cls),
            Declaration(propP), Declaration(propQ));
    }

    @Test
    public void testContainsInverseOf() {
        assertEqualsSet("InverseOf.rdf", InverseObjectProperties(P, Q));
    }

    @Test
    public void testCorrectAxiomsObjectAllValuesFrom() {
        assertEqualsSet("ObjectAllValuesFrom.rdf", SubClassOf(A, ObjectAllValuesFrom(P, B)),
            Declaration(B), Declaration(P));
    }

    @Test
    public void testCorrectAxiomsObjectCardinality() {
        assertEqualsSet("ObjectCardinality.rdf", Declaration(P),
            SubClassOf(A, ObjectExactCardinality(3, P, OWLThing())));
    }

    @Test
    public void testCorrectAxiomsObjectComplementOf() {
        assertEqualsSet("ObjectComplementOf.rdf", SubClassOf(A, ObjectComplementOf(B)));
    }

    @Test
    public void testCorrectAxiomsObjectHasSelf() {
        assertEqualsSet("ObjectHasSelf.rdf", SubClassOf(A, ObjectHasSelf(P)), Declaration(P));
    }

    @Test
    public void testCorrectAxiomsObjectHasValue() {
        assertEqualsSet("ObjectHasValue.rdf",
            SubClassOf(A, ObjectHasValue(P, NamedIndividual(iri("a")))), Declaration(P));
    }

    @Test
    public void testCorrectAxiomsObjectIntersectionOf() {
        assertEqualsSet("ObjectIntersectionOf.rdf", SubClassOf(A, ObjectIntersectionOf(B, C)));
    }

    @Test
    public void testCorrectAxiomsObjectMaxCardinality() {
        assertEqualsSet("ObjectMaxCardinality.rdf", Declaration(P),
            SubClassOf(A, ObjectMaxCardinality(3, P, OWLThing())));
    }

    @Test
    public void testCorrectAxiomsObjectMaxQualifiedCardinality() {
        assertEqualsSet("ObjectMaxQualifiedCardinality.rdf", Declaration(P),
            SubClassOf(A, ObjectMaxCardinality(3, P, B)));
    }

    @Test
    public void testCorrectAxiomsObjectMinCardinality() {
        assertEqualsSet("ObjectMinCardinality.rdf", Declaration(P),
            SubClassOf(A, ObjectMinCardinality(3, P, OWLThing())));
    }

    @Test
    public void testCorrectAxiomsObjectMinQualifiedCardinality() {
        assertEqualsSet("ObjectMinQualifiedCardinality.rdf", Declaration(P),
            SubClassOf(A, ObjectMinCardinality(3, P, B)));
    }

    @Test
    public void testCorrectAxiomsObjectOneOf() {
        OWLNamedIndividual indA = NamedIndividual(iri("a"));
        OWLNamedIndividual indB = NamedIndividual(iri("b"));
        assertEqualsSet("ObjectOneOf.rdf", SubClassOf(A, ObjectOneOf(indA, indB)));
    }

    @Test
    public void testCorrectAxiomsObjectQualifiedCardinality() {
        assertEqualsSet("ObjectQualifiedCardinality.rdf", Declaration(P),
            SubClassOf(A, ObjectExactCardinality(3, P, B)));
    }

    @Test
    public void testCorrectAxiomsObjectSomeValuesFrom() {
        assertEqualsSet("ObjectSomeValuesFrom.rdf", SubClassOf(A, ObjectSomeValuesFrom(P, B)),
            Declaration(B), Declaration(P));
    }

    @Test
    public void testCorrectAxiomsObjectUnionOf() {
        assertEqualsSet("ObjectUnionOf.rdf", SubClassOf(A, ObjectUnionOf(B, C)));
    }

    @Test
    public void testCorrectAxiomsRDFSClass() {
        OWLOntology ont = ontologyFromClasspathFile("RDFSClass.rdf");
        IRI clsIRI = IRI("http://owlapi.sourceforge.net/ontology#", "ClsA");
        OWLClass cls = Class(clsIRI);
        OWLDeclarationAxiom ax = Declaration(cls);
        assertTrue(ont.containsAxiom(ax));
    }

    @Test
    public void testStructuralReasonerRecusion() {
        OWLOntology ontology = ontologyFromClasspathFile("koala.owl");
        String ontName = ontology.getOntologyID().getOntologyIRI().get().toString();
        StructuralReasoner reasoner =
            new StructuralReasoner(ontology, new SimpleConfiguration(), BufferingMode.BUFFERING);
        OWLClass cls = Class(IRI(ontName + "#", "Koala"));
        reasoner.getSubClasses(cls, false);
        reasoner.getSuperClasses(cls, false);
    }

    @Test
    public void testCorrectAxiomsSubClassAxiom() {
        assertEqualsSet("SubClassOf.rdf", SubClassOf(A, B));
    }

    /** Tests the isGCI method on OWLSubClassAxiom */
    @Test
    public void testIsGCIMethodSubClassAxiom() {
        OWLClassExpression desc = ObjectIntersectionOf(A, C);
        OWLSubClassOfAxiom ax1 = SubClassOf(A, B);
        assertFalse(ax1.isGCI());
        OWLSubClassOfAxiom ax2 = SubClassOf(desc, B);
        assertTrue(ax2.isGCI());
    }

    @Test
    public void testParsedAxiomsSubClassOfUntypedOWLClass() {
        OWLOntology ontology = ontologyFromClasspathFile("SubClassOfUntypedOWLClass.rdf");
        List<OWLSubClassOfAxiom> axioms = asList(ontology.axioms(AxiomType.SUBCLASS_OF));
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.get(0);
        OWLClass subCls = Class(IRI(OWLAPI_TEST, "A"));
        OWLClass supCls = Class(IRI(OWLAPI_TEST, "B"));
        assertEquals(subCls, ax.getSubClass());
        assertEquals(supCls, ax.getSuperClass());
    }

    @Test
    public void testParsedAxiomsSubClassOfUntypedSomeValuesFrom() {
        OWLOntology ontology = ontologyFromClasspathFile("SubClassOfUntypedSomeValuesFrom.rdf");
        List<OWLSubClassOfAxiom> axioms = asList(ontology.axioms(AxiomType.SUBCLASS_OF));
        assertEquals(1, axioms.size());
        OWLSubClassOfAxiom ax = axioms.get(0);
        OWLClass subCls = Class(IRI(OWLAPI_TEST, "A"));
        assertEquals(subCls, ax.getSubClass());
        OWLClassExpression supCls = ax.getSuperClass();
        assertTrue(supCls instanceof OWLObjectSomeValuesFrom);
        OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) supCls;
        OWLObjectProperty property = ObjectProperty(IRI(OWLAPI_TEST, "P"));
        OWLClass fillerCls = Class(IRI(OWLAPI_TEST, "C"));
        assertEquals(property, someValuesFrom.getProperty());
        assertEquals(fillerCls, someValuesFrom.getFiller());
    }

    @Test
    public void testContainsAxiomsUntypedSubClassOf() {
        assertEqualsSet("UntypedSubClassOf.rdf", SubClassOf(A, B));
    }
}
