package tests;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import pt.iscte.esii.CovidGraphSpreadMain;

class TestCovidGraphSpreadMain {

	@Test
	final void test() throws IOException, InterruptedException, GitAPIException {
		String args[] = {};
		CovidGraphSpreadMain.main(args);
	}

}
