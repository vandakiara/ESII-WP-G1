import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;
import io.vertx.core.Vertx;

public class CovidEvolutionDiffMain {

	private static String htmlFailedPage = "Failed to load Covid Evolution";

	public static void main(String[] args) {
		Vertx.vertx().createHttpServer().requestHandler(request -> {
			ConnGit git = new ConnGit();
			HtmlDiffBuilder diffHtmlBuilder = new HtmlDiffBuilder();
			String diffHtml = null;

			/**
			 * Check if there is a new Tag available. If not, get the stored version
			 * of the Diff Page. Else, builds the new Diff Page.
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
