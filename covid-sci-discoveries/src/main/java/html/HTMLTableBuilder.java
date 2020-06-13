package html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import pdf.extractor.PDF_Extractor;

public class HTMLTableBuilder {
	private int columns;
	public static final String PATH = "D:\\University\\ES II\\PDFs";
	private final StringBuilder table = new StringBuilder();
	public ArrayList<File> filePaths;
	public ArrayList<File> previousPDFs;
	public ArrayList<File> currentPDFs;

	public static String HTML_START = "<html>";
	public static String HTML_END = "</html>";
	public static String TABLE_START_BORDER = "<table border=\"1\">";
	public static String TABLE_START = "<table>";
	public static String TABLE_END = "</table>";
	public static String HEADER_START = "<th>";
	public static String HEADER_END = "</th>";
	public static String ROW_START = "<tr>";
	public static String ROW_END = "</tr>";
	public static String COLUMN_START = "<td>";
	public static String COLUMN_END = "</td>";

	public HTMLTableBuilder(String header, boolean border, int rows, int columns, ArrayList<File> filePaths) {
		this.columns = columns;
		this.filePaths = filePaths;
		previousPDFs = new ArrayList<File>();
		currentPDFs = new ArrayList<File>();
		if (header != null) {
			table.append("<b>");
			table.append(header);
			table.append("</b>");
		}
		table.append(HTML_START);
		table.append(border ? TABLE_START_BORDER : TABLE_START);
		table.append(TABLE_END);
		table.append(HTML_END);
	}

	public String buildTable(String[][] table) {
		int counter = 0;
		int numberOfColumns = table.length;
		String[] headers = new String[numberOfColumns];
		int columnCounter = 0;
		int rowCounter = 1;
		String[] data = new String[numberOfColumns];

		while (counter < numberOfColumns) {
			headers[counter] = table[counter][0];
			counter++;
		}

		addTableHeader(headers);

		while (rowCounter < table[0].length) {
			while (columnCounter < numberOfColumns) {
				data[columnCounter] = table[columnCounter][rowCounter];
				columnCounter++;
			}
			columnCounter = 0;
			this.addRowValues(data);
			data = new String[numberOfColumns];
			rowCounter++;
		}

		return this.build();
	}

	public void addTableHeader(String... values) {
		if (values.length != columns) {
			System.out.println("Error column lenth");
		} else {
			int lastIndex = table.lastIndexOf(TABLE_END);
			if (lastIndex > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(ROW_START);
				for (String value : values) {
					sb.append(HEADER_START);
					sb.append(value);
					sb.append(HEADER_END);
				}
				sb.append(ROW_END);
				table.insert(lastIndex, sb.toString());
			}
		}
	}

	public void addRowValues(String... values) {
		if (values.length != columns) {
			System.out.println("Error column lenth");
		} else {
			int lastIndex = table.lastIndexOf(ROW_END);
			if (lastIndex > 0) {
				int index = lastIndex + ROW_END.length();
				StringBuilder sb = new StringBuilder();
				sb.append(ROW_START);
				int counter = 0;
				for (String value : values) {
					if (counter == 0) {
						sb.append(COLUMN_START);
						sb.append("<a href=\"" + filePaths.get(0).getAbsolutePath() + "`\">");
						sb.append(value);
						sb.append("</a>");
						sb.append(COLUMN_END);
						filePaths.remove(0);
						counter++;
					} else {
						sb.append(COLUMN_START);
						sb.append(value);
						sb.append(COLUMN_END);
						counter++;
					}

				}
				sb.append(ROW_END);
				table.insert(index, sb.toString());
			}
		}
	}

	public static String getHTML() {
		PDF_Extractor extractor = new PDF_Extractor(PATH);
		String[][] table = extractor.getTable();
		if (table == null) {
			System.out.println("No PDF files to extract!");
			return null;
		} else {
			HTMLTableBuilder HTMLTable = new HTMLTableBuilder(null, false, 4, 4, extractor.getPdfFileList());
			return HTMLTable.buildTable(table);
		}
	}

	public String build() {
		return table.toString();
	}

	public void setFilePaths(ArrayList<File> filePaths) {
		this.filePaths = filePaths;
	}

}