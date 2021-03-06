import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.junit.jupiter.api.Test;

class FileQueryTest {
	
	String mockFileContent = "<?xml version=\"1.0\"?>\n" + 
			"\n" + 
			"\n" + 
			"<!DOCTYPE rdf:RDF [\n" + 
			"    <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n" + 
			"    <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" + 
			"    <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n" + 
			"    <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n" + 
			"    <!ENTITY untitled-ontology-3 \"http://www.semanticweb.org/vbasto/ontologies/2020/4/untitled-ontology-3#\" >\n" + 
			"]>\n" + 
			"<rdf:RDF xmlns=\"http://www.semanticweb.org/vbasto/ontologies/2020/4/untitled-ontology-3#\"\n" + 
			"     xml:base=\"http://www.semanticweb.org/vbasto/ontologies/2020/4/untitled-ontology-3\"\n" + 
			"     xmlns:untitled-ontology-3=\"http://www.semanticweb.org/vbasto/ontologies/2020/4/untitled-ontology-3#\"\n" + 
			"     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" + 
			"     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n" + 
			"     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" + 
			"     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" + 
			"    <owl:Ontology rdf:about=\"http://www.semanticweb.org/vbasto/ontologies/2020/4/untitled-ontology-3\"/>\n" + 
			"    <owl:DatatypeProperty rdf:about=\"&untitled-ontology-3;Infecoes\"/>\n" + 
			"    <owl:DatatypeProperty rdf:about=\"&untitled-ontology-3;Internamentos\"/>\n" + 
			"    <owl:DatatypeProperty rdf:about=\"&untitled-ontology-3;Testes\"/>\n" + 
			"    <owl:Class rdf:about=\"&untitled-ontology-3;Regiao\"/>\n" + 
			"    <owl:NamedIndividual rdf:about=\"&untitled-ontology-3;Alentejo\">\n" + 
			"        <rdf:type rdf:resource=\"&untitled-ontology-3;Regiao\"/>\n" + 
			"        <Internamentos rdf:datatype=\"&xsd;integer\">50</Internamentos>\n" + 
			"        <Infecoes rdf:datatype=\"&xsd;integer\">50</Infecoes>\n" + 
			"        <Testes rdf:datatype=\"&xsd;integer\">50</Testes>\n" + 
			"    </owl:NamedIndividual>\n" + 
			"    <owl:NamedIndividual rdf:about=\"&untitled-ontology-3;Algarve\">\n" + 
			"        <rdf:type rdf:resource=\"&untitled-ontology-3;Regiao\"/>\n" + 
			"        <Infecoes rdf:datatype=\"&xsd;integer\">50</Infecoes>\n" + 
			"        <Internamentos rdf:datatype=\"&xsd;integer\">50</Internamentos>\n" + 
			"        <Testes rdf:datatype=\"&xsd;integer\">50</Testes>\n" + 
			"    </owl:NamedIndividual>\n" + 
			"</rdf:RDF>\n" + 
			"<!-- Generated by the OWL API (version 3.4.2) http://owlapi.sourceforge.net -->";
	
	@Test
	void testSearchQuery() throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		FileQuery fq = new FileQuery();
		File fileMock = File.createTempFile("covid19spreadingMock", ".rdf");
		BufferedWriter out = new BufferedWriter(new FileWriter(fileMock));
	    out.write(mockFileContent);
	    out.close();
		fq.generateHtmlFile(fileMock);
		
		String s = "//@*swrtgrswtreydrehdfhdf";		
		String resposta = fq.searchQuery(s);
		assertEquals("Bad Query", resposta);
		
		s ="//*[contains(@about,'Algarve')]/Testes/text()";
		resposta = fq.searchQuery(s);
		assertEquals("50", resposta);
	}

	@Test
	void testGenerateHtmlFile() throws InvalidRemoteException, TransportException, GitAPIException, MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {
		FileQuery fq = new FileQuery();
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
		File f = cgit.getCovFile();		
		String s = fq.generateHtmlFile(f);
		
		assertNotNull(s);
		assertNotEquals("", s);
	
	}

}
