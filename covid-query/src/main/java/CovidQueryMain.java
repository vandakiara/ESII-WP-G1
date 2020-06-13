import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.xml.sax.SAXException;

import io.vertx.core.Vertx;

public class CovidQueryMain {
	
	
	
	public static void main(String[] args) {
		ConnectGit cgit = new ConnectGit();
		try {
			cgit.cloneRepo();
		} catch (InvalidRemoteException e) {
			System.out.println("1");
			e.printStackTrace();
		} catch (TransportException e) {
			System.out.println("2");
			e.printStackTrace();
		} catch (GitAPIException e) {
			System.out.println("3");
			e.printStackTrace();
		}
		
		
		FileQuery fq = new FileQuery();
		
		Vertx.vertx().createHttpServer().requestHandler(request -> {
			
				String queryHtml = fq.generateHtmlFile(cgit.getCovFile());
			
			
			request.response().putHeader("content-type", "text/html").end(queryHtml);
		
		}).listen(3005);
		
		
		
	
		
	}
}
