package git;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

/** 
 * @author Diego Souza
 * @version 1.1
 * @since 1.0
*/

/**
 * The HtmlCovidTableBuilder will be used to build a html table
 */
public class HtmlCovidTableBuilder {
	

	 /**
	   * Sets the html table from all file versions that have tag associated
	   * @param rows The list of all file versions
	   * Each file version is DataTable object.
	   * @return this html table.
	   * @exception IOException if stream to file cannot be written to or closed.
	   */
	public String buildPage(ArrayList<DataTable> rows) {
		File htmlTemplateFile = new File("CovidGraphSpreadTemplate.html");
		
		String htmlString = null;
		try {
			htmlString = FileUtils.readFileToString(htmlTemplateFile, "utf-8");
			
			String tableRows = "";
			for (DataTable row : rows) {
				tableRows += "<tr><th>"  + row.getFileTimeStamp() +
						"</th><th>" + row.getFileName() + 
						"</th><th>" + row.getFileTag() + 
						"</th><th>" + row.getTagDescription() + 
						"</th><th> <a href='" + row.getSpreadVisualizationLink()+ "'>"+row.getSpreadVisualizationLink()+"</a></th></tr>";
			}

			htmlString = htmlString.replace("$rows", tableRows);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		return htmlString;
	}

}
