import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.junit.jupiter.api.Test;

class ConnectGitTest {

	@Test
	void testCloneRepo() throws InvalidRemoteException, TransportException, GitAPIException, MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
	}

	@Test
	void testGetCovFile() throws InvalidRemoteException, TransportException, GitAPIException, MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
		File f = cgit.getCovFile();
		
		assertEquals("covid19spreading.rdf", f.getName());
	}

}
