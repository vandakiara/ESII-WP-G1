package html;

import static org.junit.Assert.*;

import org.junit.Test;

public class HTMLTableBuilderTest {

	@Test
	public void testgetHTML() {
		String html = HTMLTableBuilder.getHTML();
		assertTrue(html != null);
		assertTrue(html instanceof String);
		assertTrue(!html.isEmpty());
	}

}
