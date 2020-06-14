package html;
import java.io.File;
import java.util.ArrayList;
import pdf.extractor.PDF_Extractor;

public class HTMLTableBuilder {
	String[][] tableOfArrays;
	String htmlCode;


	
	private final StringBuilder table = new StringBuilder();
	public ArrayList<File> filePaths;
	
	private final int columns = 4;
	public static String PATH = "D:\\University\\ES II\\PDFs";
	
	public static final String HTML_START = "<html>";
	public static final String HTML_END = "</html>";
	public static final String TABLE_START_BORDER = "<table border=\"1\">";
	// public static final String TABLE_START = "<table>";
	public static final String TABLE_END = "</table>";
	public static final String HEADER_START = "<th>";
	public static final String HEADER_END = "</th>";
	public static final String ROW_START = "<tr>";
	public static final String ROW_END = "</tr>";
	public static final String COLUMN_START = "<td>";
	public static final String COLUMN_END = "</td>";

	
	
	
	public HTMLTableBuilder(String[][] tableOfArrays) {
		this.tableOfArrays = tableOfArrays;
		
		table.append(HTML_START);
		table.append(TABLE_START_BORDER);
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

	public static String getHTMLTable() {
		PDF_Extractor extractor = new PDF_Extractor(PATH);
		HTMLTableBuilder builder = new HTMLTableBuilder(extractor.getTable());
		return "";

	}

	public String build() {
		return table.toString();
	}


}