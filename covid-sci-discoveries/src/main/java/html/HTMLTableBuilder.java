package html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTMLTableBuilder {
	/** Path to the CSV file where the metadata of the PDF files is stored. */
	private final File csvPath = new File("D:\\University\\ES II\\PDFs\\metadata.csv");
	/** Character or string to be used as a delimiter for the CSV file */
	private final String DELIMITER = ";";
	/** Path to the directory where the required PDF files are stored. */
	private final File directoryPath = new File("D:\\University\\ES II\\PDFs");
	/** StrinBuilder that will hold the html code */
	private final StringBuilder table = new StringBuilder();
	/**
	 * Number of columns that the table should have. Used for detecting unexpected
	 * values.
	 */
	private final int numberOfColumns = 4;

	/** Variables to make the construction of the table easier to read. */
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

	/**
	 * Constructor for the HTMLTableBuilder class, gives the basic opening and
	 * closing characters to the HTML table.
	 */
	public HTMLTableBuilder() {
		table.append(HTML_START);
		table.append(TABLE_START_BORDER);
		table.append(TABLE_END);
		table.append(HTML_END);
	}

	/**
	 * This method reads the CSV file from the specified directory, and inserts the
	 * data into the HTML table.
	 * 
	 * @return Returns a string with the desired HTML table.
	 */
	public String buildTable() {
		try {
			String row;
			String[] htmlRowValues = new String[4];
			String[] headers = { "Article title", "Journal name", "Publication year", "Authors" };
			addTableHeader(headers);
			BufferedReader csvReader = new BufferedReader(new FileReader(csvPath.getAbsolutePath()));
			while ((row = csvReader.readLine()) != null) {
				String[] csvLineData = row.split(DELIMITER);
				htmlRowValues[0] = csvLineData[1];
				htmlRowValues[1] = csvLineData[2];
				htmlRowValues[2] = csvLineData[3];
				htmlRowValues[3] = csvLineData[4];
				addRowValues(csvLineData[0], htmlRowValues);
			}
			csvReader.close();
		} catch (IOException e) {
			System.out.println(
					"Could not find the CSV file, or could not read from said file, while trying to construct the HTML table.");
			e.printStackTrace();
		}
		return this.build();
	}

	/**
	 * Adds table headers to the desired HTML table based on the provided list of
	 * values.
	 * 
	 * @param values Values to be turned into table headers. Each value corresponds
	 *               to one column.
	 */
	public void addTableHeader(String[] values) {
		if (values.length != numberOfColumns) {
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
	 * Adds table rows to the desired HTML table based on the provided list of
	 * values. The first row links to the original document.
	 * 
	 * @param filename Name of the original file on which this row's data was extracted from.
	 * @param values Values to be added as a row in the desired HTML table.
	 */
	public void addRowValues(String filename, String[] values) {
		if (values.length != numberOfColumns) {
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
						sb.append("<a href=\"" + directoryPath + "\\" + filename + "`\">");
						sb.append(value);
						sb.append("</a>");
						sb.append(COLUMN_END);
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

	/**
	 * Simple to-string method for this class.
	 * @return Returns a string with the HTML code.
	 */
	public String build() {
		return table.toString();
	}

}