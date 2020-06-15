
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.iscte.esii.ConnGit;

class CovidQueryMainTest {

	@BeforeEach
	void setUp() throws Exception {
		Properties properties = System.getProperties();
		properties.setProperty("vertx.disableFileCaching", "true");
	}

	@Test
	void testMain() {
		String[] args = null;
		CovidQueryMain.main(args);
		assertTrue(true);
	}

}
