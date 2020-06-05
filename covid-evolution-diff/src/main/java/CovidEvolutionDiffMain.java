import io.vertx.core.Vertx;

public class CovidEvolutionDiffMain {

	public static void main(String[] args) {
		ConnGit git = new ConnGit();
		git.getDiff();
		
//		Vertx.vertx()
//        .createHttpServer()
//        .requestHandler(request -> {
//            request.response()
//                   .putHeader("content-type", "text/html")
//                   .end("<h1>Olá cancro! :)</h1>");
//        })
//        .listen(3000);

	}

}
