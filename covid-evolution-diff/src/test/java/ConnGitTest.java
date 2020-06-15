package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.iscte.esii.ConnGit;
import pt.iscte.esii.GitDiff;

class ConnGitTest {
	ConnGit git;

	@BeforeEach
	void setUp() throws Exception {
		git = new ConnGit();
	}

	@Test
	void testGetDiff() throws InvalidRemoteException, TransportException, IOException, GitAPIException {
		GitDiff diff = git.getDiff();
		/**
		 * Should be able to return the git diff
		 */
		assertNotNull(diff);
	}

	@Test
	public void testHasNewTagTrue() throws Exception {
		boolean hasNewTag = git.hasNewTag();
		/**
		 * Should have a new available tag the first time checking
		 */
		assertTrue(hasNewTag);
	}

	@Test
	public void testHasNewTagFalse() throws Exception {
		boolean hasNewTag = git.hasNewTag();
		/**
		 * Should have a new available the first time checking
		 */
		assertTrue(hasNewTag);
		GitDiff diff = git.getDiff();
		/**
		 * Should be able to return the git diff
		 */
		assertNotNull(diff);
		hasNewTag = git.hasNewTag();
		/**
		 * Should not have another tag available
		 */
		assertFalse(hasNewTag);
	}

}
