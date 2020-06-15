package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.iscte.esii.DiffType;
import pt.iscte.esii.GitDiffChunk;

class GitDiffChunkTest {

	@Test
	void testToString() {
		GitDiffChunk chunk = new GitDiffChunk(1, 1, DiffType.NEUTRAL, "test");
		String expected = "Line Addition: 1 | Line Deletion: 1 | Type: NEUTRAL | Line: test";
		assertEquals(expected, chunk.toString());
	}

}
