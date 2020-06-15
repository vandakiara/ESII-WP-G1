
import java.io.IOException;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * ISCTE-IUL -> ES2 -> 2019/2020
 * @author jmalo1 (Joao Louro )
 * N� Aluno 82544
 * Grupo 1 * 
 *
 */


public class CovidQueryMain {	

	public static void main(String[] args) {
		
		Properties properties = System.getProperties();
		properties.setProperty("vertx.disableFileCaching", "true");

		//creates cgit for git connection and clones repository
		ConnectGit cgit = new ConnectGit();
		
		try {
			cgit.cloneRepo();
		} catch (GitAPIException | IOException e) {
			e.printStackTrace();
		}

		//creates a FileQuery object that later generates the html form and answers the queries
		FileQuery fq = new FileQuery();


		//Use for java-html connection through socket
		Vertx vertx = Vertx.vertx();
		Router router = Router.router(vertx);

		// Enable multipart form data parsing
		router.route().handler(BodyHandler.create());

		//Upon first request, gets que .rdf file from que github and gives form
		router.route("/").handler(routingContext -> {
			routingContext.response().putHeader("content-type", "text/html").end(
					fq.generateHtmlFile(cgit.getCovFile())
					);		
		});

		// handle the form request and gives answer to /search path
		router.get("/search").handler(ctx -> {
			String queryRequest= ctx.request().getParam("query");
			String responseQuery = fq.searchQuery(queryRequest);
			ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/html").end(
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
							"  </head>\r\n" + 
							"  <body>\r\n" + 
							"    <p class=\"h4\"><strong>Query result: "+responseQuery+"</strong></p>\r\n" + 
							"    <button type=\"button\" class=\"btn btn-primary\" onclick=\"goBack()\">\r\n" + 
							"      New query\r\n" + 
							"    </button>\r\n" + 
							"    <script>\r\n" + 
							"      function goBack() {\r\n" + 
							"        window.history.back();\r\n" + 
							"      }\r\n" + 
							"    </script>\r\n" + 
							"  </body>\r\n" + 
							"</html>"
							
					); });
		//listens to 3003 for requests/answers between java and html 
		vertx.createHttpServer().requestHandler(router).listen(3003);

	}
}
