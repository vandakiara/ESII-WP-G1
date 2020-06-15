package html;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class HTMLTableBuilderTest {

	/**
	 * Tests if the HTMLTableBuilder constructor actually returns anything.
	 */
	@Test
	public void testHTMLBuilder() {
		HTMLTableBuilder builder = new HTMLTableBuilder();
		assertTrue(builder != null);
	}
	
	/**
	 * Tests if the build table command returns a non-empty string.
	 */
	@Test
	public void testBuildTable() {
		HTMLTableBuilder builder = new HTMLTableBuilder();
		String html = builder.buildTable();
		assertTrue(html != null);
		assertTrue(html instanceof String);
		assertTrue(!html.isEmpty());
	}

}
