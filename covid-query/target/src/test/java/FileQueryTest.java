import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.jupiter.api.Test;

class FileQueryTest {
	
	
	
	@Test
	void testSearchQuery() throws InvalidRemoteException, TransportException, GitAPIException {
		FileQuery fq = new FileQuery();
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
		File f = cgit.getCovFile();
		fq.generateHtmlFile(f);		
		
		String s = "//@*swrtgrswtreydrehdfhdf";		
		String resposta = fq.searchQuery(s);
		assertEquals("Bad Query", resposta);
		
		s ="//*[contains(@about,'Algarve')]/Testes/text()";
		resposta = fq.searchQuery(s);
		assertEquals("50", resposta);
	}

	@Test
	void testGenerateHtmlFile() throws InvalidRemoteException, TransportException, GitAPIException {
		FileQuery fq = new FileQuery();
		ConnectGit cgit = new ConnectGit();
		cgit.cloneRepo();
		File f = cgit.getCovFile();		
		String s = fq.generateHtmlFile(f);
		
		assertEquals("Success", s);
	
	}

}
