package html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.ini4j.Wini;

public class HTMLTableBuilder {
	/** Based on https://gist.github.com/2sbsbsb/2951464 */

	/** Path to the CSV file where the metadata of the PDF files is stored. */
	private File csvPath;
	/** Character or string to be used as a delimiter for the CSV file */
	private final String DELIMITER = ";";
	/** Path to the file directory, for the table links. */
	private String hrefPath;
	/** StringBuilder that will hold the HTML code */
	private final StringBuilder table = new StringBuilder();
	/**
	 * Number of columns that the table should have. Used for detecting unexpected
	 * values.
	 */
	private final int numberOfColumns = 4;

	/** Variables to make the construction of the table easier to read. */
	public static final String HTML_START = "<html>";
	public static final String HTML_END = "</html>";
	public static final String TABLE_START_BORDER = "<table class=\"table\" border=\"1\">";
	public static final String TABLE_END = "</table>";
	public static final String HEAD_START = "<head>";
	public static final String HEAD_END = "</head>";
	public static final String BODY_START = "<body>";
	public static final String BODY_END = "</body>";
	public static final String HEADER_START = "<th>";
	public static final String HEADER_END = "</th>";
	public static final String ROW_START = "<tr>";
	public static final String ROW_END = "</tr>";
	public static final String COLUMN_START = "<td>";
	public static final String COLUMN_END = "</td>";

	/** Path to the INI configuration file. */
	private final String CONFIG = "assets/config.ini";

	/**
	 * Constructor for the HTMLTableBuilder class, gives the basic opening and
	 * closing characters to the HTML table.
	 */
	public HTMLTableBuilder() {
		Wini ini;
		try {
			ini = new Wini(new File(CONFIG));
			csvPath = new File(ini.get("Paths", "csvPath"));
			hrefPath = ini.get("Paths", "hrefPath");
		} catch (Exception e) {
			System.out.println("Error while trying to read ini file.\n");
		}

		table.append(HTML_START);
		table.append(HEAD_START);
		table.append("<meta charset=\"UTF-8\" />");
		table.append("<!-- Latest compiled and minified CSS -->\r\n"
				+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\r\n"
				+ "\r\n" + "<!-- Optional themes -->\r\n"
				+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">\r\n"
				+ "\r\n" + "<!-- Latest compiled and minified JavaScript -->\r\n"
				+ "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
		table.append(HEAD_END);
		table.append(BODY_START);
		table.append(TABLE_START_BORDER);
		table.append(TABLE_END);
		table.append(BODY_END);
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
			int counter = 0;
			addTableHeader(headers);
			BufferedReader csvReader = new BufferedReader(new FileReader(csvPath.getAbsolutePath()));
			while ((row = csvReader.readLine()) != null) {
				String[] csvLineData = row.split(DELIMITER);
				htmlRowValues[0] = csvLineData[1];
				htmlRowValues[1] = csvLineData[2];
				htmlRowValues[2] = csvLineData[3];
				htmlRowValues[3] = csvLineData[4];
				addRowValues(csvLineData[0], htmlRowValues);
				counter++;
			}
			csvReader.close();
			if (counter == 0) {
				System.out.println("There are no files from which to make a table out of.\n");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Could not find the CSV file, or could not read from said file, while trying to construct the HTML table. Verify if the path to the directory is properly set on the config.ini, and if said file is present onthe directory from which you are trying to run this service.\\n");
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
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(ROW_START);
				for (String value : values) {
					stringBuilder.append(HEADER_START);
					stringBuilder.append(value);
					stringBuilder.append(HEADER_END);
				}
				stringBuilder.append(ROW_END);
				table.insert(lastIndex, stringBuilder.toString());
			}
		}
	}

	/**
	 * Adds table rows to the desired HTML table based on the provided list of
	 * values. The first row links to the original document.
	 * 
	 * @param filename Name of the original file on which this row's data was
	 *                 extracted from.
	 * @param values   Values to be added as a row in the desired HTML table.
	 */
	public void addRowValues(String filename, String[] values) {
		if (values.length != numberOfColumns) {
			System.out.println("Error column lenth");
		} else {
			int lastIndex = table.lastIndexOf(ROW_END);
			if (lastIndex > 0) {
				int index = lastIndex + ROW_END.length();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(ROW_START);
				int counter = 0;
				for (String value : values) {
					if (counter == 0) {
						stringBuilder.append(COLUMN_START);
						stringBuilder.append("<a target=\"_blank\" href=\"" + hrefPath + "\\" + filename + "\">");
						stringBuilder.append(value);
						stringBuilder.append("</a>");
						stringBuilder.append(COLUMN_END);
						counter++;
					} else {
						stringBuilder.append(COLUMN_START);
						stringBuilder.append(value);
						stringBuilder.append(COLUMN_END);
						counter++;
					}

				}
				stringBuilder.append(ROW_END);
				table.insert(index, stringBuilder.toString());
			}
		}
	}

	/**
	 * Simple to-string method for this class.
	 * 
	 * @return Returns a string with the HTML code.
	 */
	public String build() {
		return table.toString();
	}

}