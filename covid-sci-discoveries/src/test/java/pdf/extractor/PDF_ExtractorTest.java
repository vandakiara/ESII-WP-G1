package pdf.extractor;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PDF_ExtractorTest {

	@Test
	public void testConstructor() {
		String PATH = "D:\\University\\ES II\\PDFs";
		PDF_Extractor extractor = new PDF_Extractor(PATH);
		assertTrue(extractor != null);
	}

	@Test
	public void testPopulationOfFileList() {
		String PATH = "D:\\University\\ES II\\PDFs";
		PDF_Extractor extractor = new PDF_Extractor(PATH);
		extractor.populateFilesList();
		assertTrue(extractor.getPdfFileList() != null);
		assertTrue(extractor.getPdfFileList().size() >= 0);
	}
}
