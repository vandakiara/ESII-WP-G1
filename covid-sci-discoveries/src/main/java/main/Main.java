package main;

import pdf.extractor.PDF_Extractor;

public class Main {

	public static void main(String[] args) {
		PDF_Extractor extractor = new PDF_Extractor("D:\\University\\ES II\\PDFs");
		extractor.populateFilesList();
		extractor.populateElementList();
		extractor.printAllXML();
	}

}
