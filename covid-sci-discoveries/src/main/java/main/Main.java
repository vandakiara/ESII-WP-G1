package main;

import html.HTMLTableBuilder;
import pdf.extractor.PDF_Extractor;

public class Main {

	public static void main(String[] args) {
		PDF_Extractor extractor = new PDF_Extractor("D:\\University\\ES II\\PDFs");
		extractor.populateFilesList();
		extractor.populateElementList();
		String[][] table = extractor.getTable();
		HTMLTableBuilder HTMLTable = new HTMLTableBuilder(null, false, 4, 4);
		System.out.println(HTMLTable.buildTable(table));
	}

}
