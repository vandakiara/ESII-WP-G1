package tests;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import pt.iscte.esii.CovidEvolutionDiffMain;

class CovidEvolutionDiffMainTest {

	/**
	 * Attempts to initialise the main app. Fails if anything goes wrong during this
	 * process.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test
	final void test() throws IOException, InterruptedException {
		String args[] = {};
		CovidEvolutionDiffMain.main(args);
	}

}
