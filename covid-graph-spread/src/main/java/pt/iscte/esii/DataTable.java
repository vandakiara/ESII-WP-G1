package pt.iscte.esii;

/** 
 * @author Diego Souza
 * @version 1.1
 * @since 1.0
*/

/**
 * The Data Table will be used to build an html table
 * Each record represents a covid19spreading.rdf file version that have tag associated
 * Each record has the File timestamp, File name, File tag,
 * TagDescription and Spread Visualization Link attributes.
 */
public class DataTable {
	
	/** Represents the file stamp.*/
	private String fileTimeStamp;
	
	/** Represents the file name.*/
	private String fileName;
	
	/** Represents the file tag.*/
	private String fileTag;
	
	/** Represents the tag decription.*/
	private String tagDescription;
	
	/** Represents the spread visualization link.*/
	private String spreadVisualizationLink;
	
	/** Create a covid19spreading.rdf file version that have tag associated
	 * @param fileTimeStamp The file timestamp. 
	 * @param fileName The file name.
	 * @param fileTag The file tag.
	 * @param tagDescription The file description.
	 * @param spreadVisualizationLink The spread visualization link.
	*/
	public DataTable (String fileTimeStamp, String fileName, String fileTag, 
			String tagDescription, String spreadVisualizationLink) {
		this.fileTimeStamp = fileTimeStamp;
		this.fileName = fileName;
		this.fileTag = fileTag;
		this.tagDescription = tagDescription;
		this.spreadVisualizationLink = spreadVisualizationLink;
	}
	
	 /**
	   * Gets the file timestamp of a file version that have tag associated
	   * @return this file timestamp 
	   */
	public String getFileTimeStamp() {
		return fileTimeStamp;
	}
	
	 /**
	   * Gets the file name of a file version that have tag associated
	   * @return this file name
	   */
	public String getFileName() {
		return fileName;
	}

	 /**
	   * Gets the file tag of a file version that have tag associated
	   * @return this file tag
	   */
	public String getFileTag() {
		return fileTag;
	}
	
	 /**
	   * Gets the file description of a file version that have tag associated
	   * @return this file description
	   */
	public String getTagDescription() {
		return tagDescription;
	}
	
	 /**
	   * Gets the spread visualization link of a file version that have tag associated
	   * @return this spread visualization link
	   */
	public String getSpreadVisualizationLink() {
		return spreadVisualizationLink;
	}
}
