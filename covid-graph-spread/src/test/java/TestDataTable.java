package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.iscte.esii.DataTable;

@RunWith(JUnitPlatform.class)
class TestDataTable {
	
	DataTable row;

	@BeforeEach
	void setUp() throws Exception {
		row = new DataTable("File Timestamp", "File Name", "Tag Name",
				"Tag Description", "Spread Visualization Link");
	}

	@Test
	void testGetFileTimeStamp() {
		String timestamp = row.getFileTimeStamp();
		assertEquals("File Timestamp", timestamp);
	}
	
	@Test
	void testGetFileName() {
		String fileName = row.getFileName();
		assertEquals("File Name", fileName);
	}

	@Test
	void testGetFileTag() {
		String fileTag = row.getFileTag();
		assertEquals("Tag Name", fileTag);
	}
	
	@Test
	void testGetTagDescription() {
		String tagDescription = row.getTagDescription();
		assertEquals("Tag Description", tagDescription);
	}
	
	@Test
	void testGetSpreadVisualizationLink() {
		String link = row.getSpreadVisualizationLink();
		assertEquals("Spread Visualization Link", link);
	}
}
