package pt.iscte.esii;

import org.eclipse.jgit.api.errors.GitAPIException;
import io.vertx.core.Vertx;

/** 
 * @author Diego Souza
 * @version 1.1
 * @since 1.0
*/

/**
 * The CovidGraphSpreadMain will be used to build a Web Service from the Vertex
 * This main action connects to Git,
 * Accesses the covid19spreading.rdf file available in the repository,
 * Retrieves all covid19spreading.rdf file versions that have tags associated,
 * and builds a html table.
 */
public class CovidGraphSpreadMain {
	
	private static String failedPage = "Failed to load Covid Graph Spread";
	static CovidGraphSpread covidGraphSpread = new CovidGraphSpread();
	static String fileVersionsHTML = null;
	
	/**Main method builds the web service with html table
	* @throws GitAPIException if it has error thrown by Git API commands 
	* stream to file cannot be written to or closed.
	*/
	public static void main(String[] args){
			Vertx.vertx()
        .createHttpServer()
        .requestHandler(request -> {
        	
        	if (covidGraphSpread.hasNewTag()) {
	        	covidGraphSpread.connectionGit();
				fileVersionsHTML = covidGraphSpread.buildTableHtml();
			}
        	
			if (fileVersionsHTML == null) {
				fileVersionsHTML = failedPage;
			}
			
		 request.response()
         .putHeader("content-type", "text/html")
         .end(fileVersionsHTML);
	})
	.listen(3001);
	}

}
