package tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import pt.iscte.esii.DataTable;
import pt.iscte.esii.HtmlCovidTableBuilder;

import org.apache.commons.lang3.StringUtils;

class TestHtmlCovidTableBuilder {

	ArrayList<DataTable> rows;

	@Test
	void testGetBuildPage() {
		DataTable row1 = new DataTable("File Timestamp 1", "File Name 1", "Tag Name 1", "Tag Description 1", "Spread Visualization Link 1");
		DataTable row2 = new DataTable("File Timestamp 2", "File Name 2", "Tag Name 2", "Tag Description 2", "Spread Visualization Link 2");
		DataTable row3 = new DataTable("File Timestamp 3", "File Name 3", "Tag Name 3", "Tag Description 3", "Spread Visualization Link 3");
		rows = new ArrayList<DataTable>();
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		HtmlCovidTableBuilder htmlBuilder = new HtmlCovidTableBuilder();
		htmlBuilder.buildPage(rows);
		String html = htmlBuilder.getHtmlPage();
		assertNotNull(html);
		assertTrue(html.contains("File Timestamp 1"));
		assertTrue(html.contains("File Timestamp 2"));
		assertTrue(html.contains("File Timestamp 3"));
		assertTrue(html.contains("Tag Name 1"));
		assertTrue(html.contains("Tag Name 2"));
		assertTrue(html.contains("Tag Name 3"));
		assertTrue(html.contains("Tag Description 1"));
		assertTrue(html.contains("Tag Description 2"));
		assertTrue(html.contains("Tag Description 3"));
		assertTrue(html.contains("Spread Visualization Link 1"));
		assertTrue(html.contains("Spread Visualization Link 2"));
		assertTrue(html.contains("Spread Visualization Link 3"));
		assertEquals(1, StringUtils.countMatches(html, "File Timestamp 1"));
		assertEquals(1, StringUtils.countMatches(html, "File Timestamp 2"));
		assertEquals(1, StringUtils.countMatches(html, "File Timestamp 3"));
		assertEquals(1, StringUtils.countMatches(html, "Tag Name 1"));
		assertEquals(1, StringUtils.countMatches(html, "Tag Name 2"));
		assertEquals(1, StringUtils.countMatches(html, "Tag Name 3"));
		assertEquals(1, StringUtils.countMatches(html, "Tag Description 1"));
		assertEquals(1, StringUtils.countMatches(html, "Tag Description 2"));
		assertEquals(1, StringUtils.countMatches(html, "Tag Description 3"));
		assertEquals(2, StringUtils.countMatches(html, "Spread Visualization Link 1"));
		assertEquals(2, StringUtils.countMatches(html, "Spread Visualization Link 2"));
		assertEquals(2, StringUtils.countMatches(html, "Spread Visualization Link 3"));


	}
}