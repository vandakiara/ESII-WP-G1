import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * ISCTE-IUL -> ES2 -> 2019/2020
 * @author jmalo1 (Joao Louro )
 * Nº Aluno 82544
 * Grupo 1 * 
 *
 */


public class FileQuery {
	//XML version of the .rdf file 
	private Document doc;
	//variable for XPath query
	private XPath xpath;
	//receives the XPath expression
	private XPathExpression expr;
	//html form to return 
	private String htmlPage = "";

	private XPathFactory xpathFactory;

	//search the doc file using XML query, returns answer with the query is valid, otherwise returns Bad Query
	public String searchQuery(String query) {

		xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();
		String answer = "";
		try {				
			expr = xpath.compile(query);		
			answer = (String) expr.evaluate(doc, XPathConstants.STRING);	
			System.out.println(expr.evaluate(doc, XPathConstants.STRING));
			System.out.println(answer);
		} catch (Exception e) { 
			answer= "Bad Query";
		}
		return answer;
	}


	//Genetares the Html form and returns it as a string
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

		htmlPage="<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"  <head>\r\n" + 
				"    <meta charset=\"UTF-8\" />\r\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n" + 
				"    <link\r\n" + 
				"      rel=\"stylesheet\"\r\n" + 
				"      href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\"\r\n" + 
				"    />\r\n" + 
				"    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\r\n" + 
				"    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\r\n" + 
				"    <style>\r\n" + 
				"      label {\r\n" + 
				"        width: 100%;\r\n" + 
				"      }\r\n" + 
				"      input.form-control {\r\n" + 
				"        display: inline;\r\n" + 
				"        width: 75%;\r\n" + 
				"      }\r\n" + 
				"      .dropdown {\r\n" + 
				"        display: inline;\r\n" + 
				"      }\r\n" + 
				"    </style>\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <div class=\"container\">\r\n" + 
				"      <form action=\"/search\" method=\"get\">\r\n" + 
				"        <div class=\"form-group\">\r\n" + 
				"          <label for=\"result\">Insert your query here:</label>\r\n" + 
				"          <input class=\"form-control\" type=\"text\" name=\"query\" id=\"result\" />\r\n" + 
				"          <input class=\"btn btn-primary\" type=\"submit\" value=\"Submit\" /><br />\r\n" + 
				"          <br />\r\n" + 
				"          <div class=\"btn-group\">\r\n" + 
				"            <div class=\"dropdown\">\r\n" + 
				"              <button\r\n" + 
				"                class=\"btn btn-secondary dropdown-toggle\"\r\n" + 
				"                type=\"button\"\r\n" + 
				"                id=\"dropdown-elements\"\r\n" + 
				"                data-toggle=\"dropdown\"\r\n" + 
				"                aria-haspopup=\"true\"\r\n" + 
				"                aria-expanded=\"false\"\r\n" + 
				"              >\r\n" + 
				"                Elements<span class=\"caret\"></span>\r\n" + 
				"              </button>\r\n" + 
				"\r\n" + 
				"              <ul class=\"dropdown-menu\">\r\n";
				xpathFactory = XPathFactory.newInstance();
				xpath = xpathFactory.newXPath();
				String query = "/RDF/NamedIndividual/@*";				
				//queries for all elements inside doc
				addToHtmlPageDropDownList(query);				
				
	  htmlPage+="              </ul>\r\n" + 
				"            </div>\r\n" + 
				"\r\n" + 
				"            <div class=\"dropdown\">\r\n" + 
				"              <button\r\n" + 
				"                class=\"btn btn-secondary dropdown-toggle\"\r\n" + 
				"                type=\"button\"\r\n" + 
				"                id=\"dropdown-attributes\"\r\n" + 
				"                data-toggle=\"dropdown\"\r\n" + 
				"                aria-haspopup=\"true\"\r\n" + 
				"                aria-expanded=\"false\"\r\n" + 
				"              >\r\n" + 
				"                Attributes<span class=\"caret\"></span>\r\n" + 
				"              </button>\r\n" + 
				"              <ul class=\"dropdown-menu\">\r\n";
				//queries for all attributes inside doc
				query = "/RDF/DatatypeProperty/@*";
				addToHtmlPageDropDownList(query);
	  
	  
	  htmlPage+="              </ul>\r\n" + 
				"            </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </form>\r\n" + 
				"    </div>\r\n" + 
				"    <script>\r\n" + 
				"      function dis(val) {\r\n" + 
				"        document.getElementById(\"result\").value += val;\r\n" + 
				"      }\r\n" + 
				"    </script>\r\n" + 
				"  </body>\r\n" + 
				"</html>";
		
		return htmlPage;
	}

	//Function that when called adds elements to html form dropdowns  
	void addToHtmlPageDropDownList(String query){
		try {
			expr = xpath.compile(query);
		} catch (XPathExpressionException e) {			
			e.printStackTrace();
		}		
		NodeList nl;
		try {
			nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				htmlPage+="<li><a onclick=\"dis('"
						+StringUtils.substringAfter(nl.item(i).getNodeValue(), "#")
						+"')\">"+StringUtils.substringAfter(nl.item(i).getNodeValue(), "#")+"</a></li>\n";				
			}
		} catch (XPathExpressionException e) {			
			e.printStackTrace();
		}
	}


}
