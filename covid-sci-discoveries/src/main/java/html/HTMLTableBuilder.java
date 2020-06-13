package html;

import java.io.File;
import java.util.ArrayList;

public class HTMLTableBuilder {
	private final StringBuilder table = new StringBuilder();
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
	private int columns;
	public ArrayList<File> filePaths;

	/**
	 * @param header
	 * @param border
	 * @param rows
	 * @param columns
	 */
	public HTMLTableBuilder(String header, boolean border, int rows, int columns, ArrayList<File> filePaths) {
		this.columns = columns;
		this.filePaths = filePaths;
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

		while (counter < numberOfColumns) {
			headers[counter] = table[counter][0];
			counter++;
		}
		addTableHeader(headers);

		int columnCounter = 0;
		int rowCounter = 1;
		String[] data = new String[numberOfColumns];

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

	/**
	 * @param values
	 */
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

	/**
	 * @param values
	 */
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
					}else {
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

	/**
	 * @return
	 */
	public String build() {
		return table.toString();
	}

	public void setFilePaths(ArrayList<File> filePaths) {
		this.filePaths = filePaths;
	}

}