import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileQuery {

	private Document doc;
	private XPath xpath;
	private XPathExpression expr;

	public String searchQuery( String query) {
		String answer = "";
		try {	
			expr = xpath.compile(query);
			answer = (String) expr.evaluate(doc, XPathConstants.STRING);	

		} catch (Exception e) { 
			answer= "Bad Query";
		}

		return answer;

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
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}


		XPathFactory xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();


		String elementsString = "";
		String attributesString = "";

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
			for (int i = 0; i < nl.getLength(); i++) 		
				elementsString+= StringUtils.substringAfter(nl.item(i).getNodeValue(), "#")+"\n";

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
				attributesString +=StringUtils.substringAfter(nls.item(i).getNodeValue(), "#")+"\n";
			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		String path = "src/htmlFormFile.html";

		String htmlPage = "<form action=\"CovidQueryMain\" method=\"post\">\n";

		String elements = "";

		htmlPage+="<label for=\"areaelementos\">Elementos</label>\n "
				+ "<textarea id=\"areaelementos\" name=\"areaelementos\" rows=\"10\" cols=\"30\">\n"
				+ elementsString + "</textarea>"
				+ "<label for=\"areaAtributos\">Atributos</label>"
				+ "<textarea id=\"areaAtributos\" name=\"areaAtributos\" rows=\"10\" cols=\"30\">"
				+ attributesString + "</textarea>"
				+ "<br><br>";

		
		htmlPage+="<label for=\"name\">Query:</label>\n";
		htmlPage+="<input type=\"text\" id=\"name\" name=\"user_name\">\n";	
		htmlPage+="<input type=\"submit\" value=\"Submeter\">\n";
		htmlPage+="</form>";
		
		htmlPage+="<br>";
		htmlPage+="<label for=\"result\">Result</label>";
		htmlPage+="<textarea id=\"result\" name=\"result\" rows=\"1\" cols=\"40\">";
		htmlPage+="";
		htmlPage+="</textarea>";

		try {
			Files.writeString(Paths.get(path), htmlPage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return "Success";
	}



}
