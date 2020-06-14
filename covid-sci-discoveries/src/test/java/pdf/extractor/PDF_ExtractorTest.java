package pdf.extractor;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PDF_ExtractorTest {

	@Test
	public void testConstructor() {
		PDF_Extractor extractor = new PDF_Extractor();
		assertTrue(extractor != null);
	}

	@Test
	public void testPopulationOfFileList() {
		PDF_Extractor extractor = new PDF_Extractor();
		extractor.createCSVIfNotExist();
		extractor.populateFilesList();
		assertTrue(extractor.getPdfFileList() != null);
		assertTrue(extractor.getPdfFileList().size() >= 0);
	}
}
