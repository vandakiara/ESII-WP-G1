import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileQuery {

	private Document doc;
	private XPath xpath;
	private XPathExpression expr;

	public String searchQuery(File file, String q) {
		try {	



			String query = "/RDF/NamedIndividual/@*";
			System.out.println("Query para obter a lista das regiões: " + query);



			expr = xpath.compile(query);         


			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
			}



			query = "//*[contains(@about,'Algarve')]/Testes/text()";  
			System.out.println("Query para obter o número de testes feitos no Algarve: " + query);
			expr = xpath.compile(query);     
			System.out.println(expr.evaluate(doc, XPathConstants.STRING));

			query = "//*[contains(@about,'Algarve')]/Infecoes/text()";
			System.out.println("Query para obter o número de infeções no Algarve: " + query);
			expr = xpath.compile(query);
			System.out.println(expr.evaluate(doc, XPathConstants.STRING));

			query = "//*[contains(@about,'Algarve')]/Internamentos/text()";
			System.out.println("Query para obter o número de internamentos no Algarve: " + query);
			expr = xpath.compile(query);     
			System.out.println(expr.evaluate(doc, XPathConstants.STRING));

		} catch (Exception e) { e.printStackTrace(); }

		return null;
	}

	public String generateHtmlFile(File covFile)  {
		File inputFile = covFile;    	      	  
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			try {
				doc = dBuilder.parse(inputFile);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		XPathFactory xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();


		ArrayList<String> elements = new ArrayList<String>();

		String query = "/RDF/NamedIndividual/@*";
		try {
			expr = xpath.compile(query);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nl;
		try {
			nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				elements.add(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		ArrayList<String> attributes = new ArrayList<String>();

		query = "/RDF/DatatypeProperty/@*";
		try {
			expr = xpath.compile(query);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nls;
		try {
			nls = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nls.getLength(); i++) {
				attributes.add(StringUtils.substringAfter(nls.item(i).getNodeValue(), "#"));
			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		return null;
	}

	

}
