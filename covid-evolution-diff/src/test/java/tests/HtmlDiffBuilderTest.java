package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.iscte.esii.DiffType;
import pt.iscte.esii.GitDiff;
import pt.iscte.esii.GitDiffChunk;
import pt.iscte.esii.HtmlDiffBuilder;

class HtmlDiffBuilderTest {

	GitDiff diff;
	HtmlDiffBuilder htmlBuilder;

	@BeforeEach
	void setUp() throws Exception {
		List<GitDiffChunk> chunks = new ArrayList<GitDiffChunk>();
		chunks.add(new GitDiffChunk(1, 1, DiffType.NEUTRAL, "neutral line"));
		chunks.add(new GitDiffChunk(2, 1, DiffType.DELETION, "deletion line"));
		chunks.add(new GitDiffChunk(2, 2, DiffType.ADDITION, "addition line"));
		diff = new GitDiff("tagBaseTest", "tagCompareTest", chunks);

		htmlBuilder = new HtmlDiffBuilder();
	}

	@Test
	void testBuildDiffPage() throws IOException {
		String htmlPage = htmlBuilder.buildDiffPage(diff);
		assertNotNull(htmlPage);
		assertTrue(htmlPage.contains("tagBaseTest"));
		assertTrue(htmlPage.contains("tagCompareTest"));
		assertEquals(1, StringUtils.countMatches(htmlPage, "<tr class=\"diff-neutral-row\">"));
		assertEquals(1, StringUtils.countMatches(htmlPage, "<tr class=\"diff-deletion-row\">"));
		assertEquals(1, StringUtils.countMatches(htmlPage, "<tr class=\"diff-addition-row\">"));
	}
	
	@Test
	void testGetHtmlPage() throws IOException {
		String htmlPage = htmlBuilder.getHtmlPage();
		/**
		 * No HTML page available if none was built yet
		 */
		assertEquals("", htmlPage);
		String htmlPageBuilt = htmlBuilder.buildDiffPage(diff);
		assertNotNull(htmlPageBuilt);
		
		htmlPage = htmlBuilder.getHtmlPage();
		
		/**
		 * After building a page, you're able to
		 * retrieve it again later without rebuilding it
		 */
		assertEquals(htmlPageBuilt, htmlPage);
	}

}
