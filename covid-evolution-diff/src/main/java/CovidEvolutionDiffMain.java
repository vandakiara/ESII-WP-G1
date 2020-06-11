import io.vertx.core.Vertx;

public class CovidEvolutionDiffMain {

	public static void main(String[] args) {
		Vertx.vertx()
        .createHttpServer()
        .requestHandler(request -> {
        	ConnGit git = new ConnGit();
        	GitDiff diff = git.getDiff();
        	
        	HtmlDiffBuilder diffHtmlBuilder = new HtmlDiffBuilder();
        	String diffHtml = diffHtmlBuilder.buildDiffPage(diff);
            request.response()
                   .putHeader("content-type", "text/html")
                   .end(diffHtml);
        })
        .listen(3000);

	}

}
