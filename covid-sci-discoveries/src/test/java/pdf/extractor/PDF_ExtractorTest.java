package pdf.extractor;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.io.File;

/**
 * This class is meant to test the PDF_Extractor class. It is assumed that there
 * is at least one PDF file with a valid format in the specified directory.
 *
 */
public class PDF_ExtractorTest {
	/** Path to the CSV file where the metadata of the PDF files is stored. */
	private final File csvPath = new File("..\\wordpress\\wp-content\\uploads\\simple-file-list");

	/**
	 * Tests if the constructor is returning anything.
	 */
	@Test
	public void testConstructor() {
		PDF_Extractor extractor = new PDF_Extractor();
		assertTrue(extractor != null);
	}

	/**
	 * Tests if the populateFilesList() method is actually creating something, and
	 * if the result has anything inside of it.
	 */
	@Test
	public void testPopulateFilesList() {
		PDF_Extractor extractor = new PDF_Extractor();
		extractor.createCSVIfNotExist();
		extractor.populateFilesList();
		assertTrue(extractor.getPdfFileList() != null);
		assertTrue(extractor.getPdfFileList().size() >= 0);
	}

	/**
	 * Tests if the method CreateCSVIfNotExist() actually creates a new CSV file in
	 * case of deletion or otherwise.
	 */
	@Test
	public void testcreateCSVIfNotExist() {
		csvPath.delete();
		PDF_Extractor extractor = new PDF_Extractor();
		extractor.createCSVIfNotExist();
		assertTrue(extractor.getCSVPath() != null);
	}

	/**
	 * Tests if the CSV file isn't accidentally deleted during the write of data
	 * into it during the extraction.
	 */
	@Test
	public void testIfCSVNotDeleted() {
		csvPath.delete();
		PDF_Extractor extractor2 = new PDF_Extractor();
		extractor2.createCSVIfNotExist();
		extractor2.populateFilesList();
		extractor2.deleteUnexistentFilesFromCSV();
		extractor2.loadListOfPDFFilesToBeExtracted();
		extractor2.printListOfPDFFilesToBeExtracted();
		extractor2.extractPDFmetadataToCSV();
		assertTrue(extractor2.getCSVPath() != null);
	}
}
