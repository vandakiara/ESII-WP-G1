package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.iscte.esii.CovidGraphSpread;

class TestCovidGraphSpread {

	CovidGraphSpread graph;
	
	@BeforeEach
	void setUp() throws Exception {
		graph = new CovidGraphSpread();
	}
	
	@Test
	void testBuildHtml() {
		/**
		 * After building a page html,
		 * the tableHtml is not null
		 */
		graph.connectionGit();
		String tableHtml = graph.buildTableHtml();
		assertNotNull(tableHtml);
	}
	
	@Test
	void testHasNewTag() {
		graph.connectionGit();
		String tableHtml = graph.buildTableHtml();
		/**
		 * After building a page html, you're able to
		 * retrieve it again later without rebuilding it
		 */
		boolean hasNewTag = graph.hasNewTag();
		assertEquals(false, hasNewTag);
	}

}
