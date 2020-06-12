package git;

import java.util.Date;

public class DataTable {
	
	private String fileTimeStamp;
	private String fileName;
	private String fileTag;
	private String tagDescription;
	private String spreadVisualizationLink;
	
	public DataTable (String string, String fileName, String fileTag, 
			String tagDescription, String spreadVisualizationLink) {
		this.fileTimeStamp = string;
		this.fileName = fileName;
		this.fileTag = fileTag;
		this.tagDescription = tagDescription;
		this.spreadVisualizationLink = spreadVisualizationLink;
	}
	
	public String getFileTimeStamp() {
		return fileTimeStamp;
	}

	public void setFileTimeStamp(String fileTimeStamp) {
		this.fileTimeStamp = fileTimeStamp;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileTag() {
		return fileTag;
	}

	public void setFileTag(String fileTag) {
		this.fileTag = fileTag;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	public String getSpreadVisualizationLink() {
		return spreadVisualizationLink;
	}

	public void setSpreadVisualizationLink(String spreadVisualizationLink) {
		this.spreadVisualizationLink = spreadVisualizationLink;
	}
	
	

}
