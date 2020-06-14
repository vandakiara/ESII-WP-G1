package main;

import html.HTMLTableBuilder;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import pdf.extractor.PDF_Extractor;

public class Main {

	public static void main(String[] args) {
		VertxOptions options = new VertxOptions();
		options.setBlockedThreadCheckInterval(2000000);
		Vertx.vertx(options).createHttpServer().requestHandler(request -> {
			request.response().putHeader("content-type", "text/html").end(auxMain());
		}).listen(3002);

	}
	
	public static String auxMain() {
		PDF_Extractor extractor = new PDF_Extractor();
		extractor.createCSVIfNotExist();
		extractor.populateFilesList();
		extractor.deleteUnexistentFilesFromCSV();
		extractor.loadListOfPDFFilesToBeExtracted();
		extractor.printListOfPDFFilesToBeExtracted();
		extractor.extractPDFmetadataToCSV();
		HTMLTableBuilder builder = new HTMLTableBuilder();
		String htmlTable = builder.buildTable();
		return htmlTable;
	}

}
