package main;

import html.HTMLTableBuilder;
import pdf.extractor.PDF_Extractor;

public class Main {

	public static void main(String[] args) {
		PDF_Extractor extractor = new PDF_Extractor();
		extractor.createCSVIfNotExist();
		extractor.populateFilesList();
		extractor.deleteUnexistentFilesFromCSV();
		extractor.loadListOfPDFFilesToBeExtracted();
		extractor.printListOfPDFFilesToBeExtracted();
		extractor.extractPDFmetadataToCSV();
	}

}
