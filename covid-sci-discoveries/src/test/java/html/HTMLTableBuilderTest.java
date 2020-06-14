package html;

import static org.junit.Assert.*;

import org.junit.Test;

public class HTMLTableBuilderTest {

	@Test
	public void testgetHTML() {
		HTMLTableBuilder builder = new HTMLTableBuilder();
		String html = builder.buildTable();
		assertTrue(html != null);
		assertTrue(html instanceof String);
		assertTrue(!html.isEmpty());
	}

}
