import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.jupiter.api.Test;

class ConnectGitTest {

	@Test
	void testCloneRepo() throws InvalidRemoteException, TransportException, GitAPIException {
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
	}

	@Test
	void testGetCovFile() throws InvalidRemoteException, TransportException, GitAPIException {
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
		
		assertNotEquals("covid19spreading.rdf", "");
	}

}
