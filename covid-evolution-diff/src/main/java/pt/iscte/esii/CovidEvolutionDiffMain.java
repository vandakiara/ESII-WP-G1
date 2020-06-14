package pt.iscte.esii;
import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;
import io.vertx.core.Vertx;

public class CovidEvolutionDiffMain {

	/**
	 * Default message when failed to generate the page for the HTML diff
	 */
	private static String htmlFailedPage = "Failed to load Covid Evolution";
	/**
	 * Reference to Connection to Git
	 */
	static ConnGit git = new ConnGit();
	/**
	 * Reference to HTML Diff Builder
	 */
	static HtmlDiffBuilder diffHtmlBuilder = new HtmlDiffBuilder();
	/**
	 * Store the built HTML page
	 */
	static String diffHtml = null;

	/**
	 * Start application
	 * @param args
	 */
	public static void main(String[] args) {
		Vertx.vertx().createHttpServer().requestHandler(request -> {
			
			/**
			 * Check if there is a new Tag available. If not, get the stored version of the
			 * Diff Page. Else, builds the new Diff Page.
			 */
			if (!git.hasNewTag()) {
				diffHtml = diffHtmlBuilder.getHtmlPage();
			} else {
				try {
					GitDiff diff = git.getDiff();
					diffHtml = diffHtmlBuilder.buildDiffPage(diff);
				} catch (IOException | GitAPIException e) {
					System.out.println("Failed to load diff for Covid Evolution: " + e);
				}
			}

			/**
			 * If failed to build Diff Page, send error message
			 */
			if (diffHtml == null) {
				diffHtml = htmlFailedPage;
			}
			request.response().putHeader("content-type", "text/html").end(diffHtml);
		}).listen(3000);

	}

}
