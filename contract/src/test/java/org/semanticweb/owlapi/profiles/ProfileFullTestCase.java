package org.semanticweb.owlapi.profiles;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;

@RunWith(Parameterized.class)
public class ProfileFullTestCase extends ProfileBase {

    private final String premise;

    public ProfileFullTestCase(String premise) {
        this.premise = premise;
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf></owl:Class><rdf:Description><rdf:type rdf:nodeID=\"B\"/></rdf:Description><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"urn:test#C\"/><rdf:Description rdf:nodeID=\"B\"/></owl:intersectionOf></owl:Class></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf></owl:Class><owl:Class rdf:about=\"urn:test#notB\"><owl:complementOf rdf:nodeID=\"B\"/></owl:Class><owl:Class rdf:about=\"urn:test#u\"><owl:unionOf rdf:parseType=\"Collection\"><rdf:Description rdf:nodeID=\"B\"/><owl:Class rdf:about=\"urn:test#A\"/></owl:unionOf></owl:Class></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf><owl:disjointWith><owl:Class rdf:about=\"urn:test#C\"/></owl:disjointWith><owl:equivalentClass><owl:Class rdf:about=\"urn:test#D\"/></owl:equivalentClass></owl:Class></rdf:RDF>",
            // @Ignore("An ontology missing the <Ontology> tag is not a valid
            // ontology, but the current OWLOntology interface does not allow
            // the profile checkers to know.") "<rdf:RDF
            // xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"
            // xmlns:owl=\"http://www.w3.org/2002/07/owl#\"
            // xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"
            // xmlns:ex=\"http://www.example.org#\"
            // xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description
            // rdf:about=\"http://www.example.org#c1\"><owl:equivalentClass
            // rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf><owl:equivalentClass><owl:Class rdf:about=\"urn:test#A\"/></owl:equivalentClass></owl:Class><rdf:Description><rdf:type rdf:nodeID=\"B\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#x\"><rdf:type rdf:resource=\"http://www.example.org#c2\"/></ex:c1><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:complementOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:x rdf:about=\"http://www.example.org#z\"><rdf:type rdf:resource=\"http://www.example.org#y\"/></ex:x><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:intersectionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:intersectionOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c rdf:about=\"http://www.example.org#z\"/><rdf:Description rdf:nodeID=\"A0\"><rdf:first rdf:resource=\"http://www.example.org#x\"/><rdf:rest rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#y\"/></rdf:rest></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:intersectionOf rdf:nodeID=\"A0\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:intersectionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:intersectionOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:x rdf:about=\"http://www.example.org#z\"/><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:unionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:unionOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:unionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:unionOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p1><rdf:Description rdf:about=\"http://www.example.org#y\"><ex:p2 rdf:resource=\"http://www.example.org#z\"/></rdf:Description></ex:p1></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#p1\"/><rdf:Description rdf:about=\"http://www.example.org#p2\"/></owl:propertyChainAxiom></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AsymmetricProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p><rdf:Description rdf:about=\"http://www.example.org#y\"><ex:p rdf:resource=\"http://www.example.org#x\"/></rdf:Description></ex:p></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:FunctionalProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#y1\"/><ex:p rdf:resource=\"http://www.example.org#y2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:InverseFunctionalProperty rdf:about=\"http://www.example.org#p\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:InverseFunctionalProperty><rdf:Description rdf:about=\"http://www.example.org#x1\"><ex:p>data</ex:p></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><ex:p>data</ex:p></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:IrreflexiveProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#x\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#e\"><owl:oneOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:oneOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x\"><owl:differentFrom rdf:resource=\"http://www.example.org#x\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#x\"/><ex:c2 rdf:about=\"http://www.example.org#y\"/><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:equivalentClass rdf:resource=\"http://www.example.org#c2\"/><owl:disjointWith rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#w\"><rdf:type rdf:resource=\"http://www.example.org#c2\"/></ex:c1><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:disjointWith rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:disjointWith rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><ex:p2 rdf:resource=\"http://www.example.org#o2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p1 rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><owl:equivalentProperty rdf:resource=\"http://www.example.org#p2\"/><owl:propertyDisjointWith rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p1 rdf:resource=\"http://www.example.org#o\"/><ex:p2 rdf:resource=\"http://www.example.org#o\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><owl:propertyDisjointWith rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p rdf:resource=\"http://www.example.org#o\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><owl:propertyDisjointWith rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#x\"/><ex:c2 rdf:about=\"http://www.example.org#y\"/><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:equivalentClass rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c2\"><rdfs:subClassOf><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdfs:subClassOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf><rdf:Description rdf:about=\"http://www.example.org#c2\"><owl:equivalentClass rdf:resource=\"http://www.example.org#d2\"/></rdf:Description></rdfs:subClassOf><owl:equivalentClass rdf:resource=\"http://www.example.org#d1\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><ex:p2 rdf:resource=\"http://www.example.org#o2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p1 rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><owl:equivalentProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><owl:equivalentProperty rdf:resource=\"http://www.example.org#q2\"/></rdf:Description></rdfs:subPropertyOf><owl:equivalentProperty rdf:resource=\"http://www.example.org#q1\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p rdf:resource=\"http://www.example.org#o\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><owl:sameAs><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p1 rdf:resource=\"http://www.example.org#o1\"/></rdf:Description></owl:sameAs></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#o2\"><owl:sameAs rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p2\"><owl:sameAs rdf:resource=\"http://www.example.org#p1\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><ex:q rdf:resource=\"http://www.example.org#o2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#q\"><owl:inverseOf rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c rdf:about=\"http://www.example.org#x\"><ex:p1 rdf:resource=\"http://www.example.org#z\"/><ex:p2>data</ex:p2></ex:c><ex:c rdf:about=\"http://www.example.org#y\"><ex:p1 rdf:resource=\"http://www.example.org#z\"/><ex:p2>data</ex:p2></ex:c><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#p1\"/><rdf:Description rdf:about=\"http://www.example.org#p2\"/></owl:hasKey></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AllDisjointClasses rdf:about=\"http://www.example.org#z\"><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#c1\"/><rdf:Description rdf:about=\"http://www.example.org#c2\"/><rdf:Description rdf:about=\"http://www.example.org#c3\"/></owl:members></owl:AllDisjointClasses><ex:c1 rdf:about=\"http://www.example.org#w\"><rdf:type rdf:resource=\"http://www.example.org#c2\"/></ex:c1></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AllDisjointProperties rdf:about=\"http://www.example.org#z\"><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#p1\"/><rdf:Description rdf:about=\"http://www.example.org#p2\"/><rdf:Description rdf:about=\"http://www.example.org#p3\"/></owl:members></owl:AllDisjointProperties><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p1 rdf:resource=\"http://www.example.org#o\"/><ex:p2 rdf:resource=\"http://www.example.org#o\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:sourceIndividual><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p>data</ex:p></rdf:Description></owl:sourceIndividual><owl:assertionProperty rdf:resource=\"http://www.example.org#p\"/><owl:targetValue>data</owl:targetValue></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:sourceIndividual><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p rdf:resource=\"http://www.example.org#o\"/></rdf:Description></owl:sourceIndividual><owl:assertionProperty rdf:resource=\"http://www.example.org#p\"/><owl:targetIndividual rdf:resource=\"http://www.example.org#o\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#u\"><ex:p rdf:resource=\"http://www.example.org#v\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:domain rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#u\"><ex:p rdf:resource=\"http://www.example.org#v\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:range rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#w\"/><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf><rdf:Description rdf:about=\"http://www.example.org#c2\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c3\"/></rdf:Description></rdfs:subClassOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p1 rdf:resource=\"http://www.example.org#o\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p3\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:domain rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:domain><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdfs:domain></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:range rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:range><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdfs:range></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:allValuesFrom><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></owl:allValuesFrom><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c2\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></owl:onProperty></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#x\"/></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:hasValue rdf:resource=\"http://www.example.org#v\"/><owl:onProperty><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></owl:onProperty></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:hasValue rdf:resource=\"http://www.example.org#v\"/><owl:onProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"/><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:hasValue rdf:resource=\"http://www.example.org#u\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:hasValue rdf:resource=\"http://www.example.org#u\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#u\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#x1\"/><ex:p rdf:resource=\"http://www.example.org#x2\"/></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#x\"/></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p><ex:c rdf:about=\"http://www.example.org#x1\"/></ex:p><ex:p><ex:c rdf:about=\"http://www.example.org#x2\"/></ex:p></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxQualifiedCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxQualifiedCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/><owl:onClass rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p><ex:c rdf:about=\"http://www.example.org#x\"/></ex:p></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxQualifiedCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxQualifiedCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/><owl:onClass rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:someValuesFrom><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></owl:someValuesFrom><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c2\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></owl:onProperty></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#w\"><ex:p><ex:c rdf:about=\"http://www.example.org#x\"/></ex:p></rdf:Description></rdf:RDF>");
    }

    @Test
    public void testFull() {
        test(premise.startsWith("<rdf:RDF") ? new RDFXMLDocumentFormat()
            : new FunctionalSyntaxDocumentFormat(), premise, false, false, false, false);
    }
}
