package pdf.extractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.ini4j.Wini;
import org.jdom.Element;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

public class PDF_Extractor {
	/** Path to the directory where the required PDF files are stored. */
	private File directoryPath;
	/** Path to the CSV file where the metadata of the PDF files is stored. */
	private File csvPath;
	/** List of PDF files currently in the directory. */
	private ArrayList<File> pdfFileList;
	/** List of PDF files that need to be extracted into the CSV file. */
	private ArrayList<File> pdfFileToBeExtractedList;
	/** Character or string to be used as a delimiter for the CSV file */
	private final String DELIMITER = ";";
	/** Path to the INI configuration file. */
	private final String CONFIG = "assets/config.ini";

	/**
	 * Creates a PDF_Extractor object, used to extract specific metadata from PDF
	 * files.
	 */
	public PDF_Extractor() {
		Wini ini;
		try {
			ini = new Wini(new File(CONFIG));
			directoryPath = new File(ini.get("Paths", "simpleFilesPath"));
			csvPath = new File(ini.get("Paths", "csvPath"));
		} catch (Exception e) {
			System.out.println("Error while trying to read ini file. Could not start the path variables.\n");
			System.out.println("Directory path: " + directoryPath);
			System.out.println("CSV path: " + csvPath);
		}
		pdfFileList = new ArrayList<File>();
		pdfFileToBeExtractedList = new ArrayList<File>();
	}

	/**
	 * Checks to see if the CSV file exists, and creates it if it doesn't.
	 */
	public void createCSVIfNotExist() {
		try {
			if (!(csvPath.exists())) {
				csvPath.createNewFile();
			}
		} catch (Exception e) {
			System.out.println(
					"Could not create CSV file. Verify if the config.ini file is properly defined on the directory on which you are trying to run this service.");
		}

	}

	/** Generates a list of the PDF files in the directory. */
	public void populateFilesList() {
		try {
			for (File fileEntry : directoryPath.listFiles()) {
				if (fileEntry.exists() && !fileEntry.isDirectory() && fileEntry.getName().endsWith(".pdf")) {
					pdfFileList.add(fileEntry);
					System.out.println("File exists in directory: " + fileEntry.getName());
				}
			}
		} catch (Exception e) {
			System.out.println(
					"Couldn't access PDF files. Verify if the path to the directory is properly set on the config.ini, and if said file is present onthe directory from which you are trying to run this service.");
			System.out.println("Directory: " + directoryPath);
			System.out.println("CSV: " + csvPath);
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
		}
	}

	/**
	 * Compares the existent PDF files in the object's specified directory to that
	 * of the CSV file. If there are any files in the CSV that are no longer present
	 * in the directory, these are deleted from the CSV file.
	 */
	public void deleteUnexistentFilesFromCSV() {
		String row;
		File tempFile = null;
		try {
			tempFile = new File(csvPath.getAbsolutePath().replaceAll(csvPath.getName(), "tempFile"));
			int count = 0;
			BufferedReader csvReader = new BufferedReader(new FileReader(csvPath.getAbsolutePath()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			while ((row = csvReader.readLine()) != null) {
				String[] csvLineData = row.split(DELIMITER);
				boolean existsInDirectory = false;
				for (File pdfFile : pdfFileList) {
					if (pdfFile.getName().equals(csvLineData[0])) {
						existsInDirectory = true;
					}
				}
				if (existsInDirectory) {
					if (count == 0) {
						writer.write(row);
						count++;
					} else
						writer.write("\n" + row);
				}

			}
			writer.close();
			csvReader.close();
			csvPath.delete();
			if (tempFile != null)
				tempFile.renameTo(csvPath);
		} catch (Exception e) {
			System.out.println(
					"An error occurred while trying to detected deleted files. Verify if the path to the directory is properly set on the config.ini, and if said file is present onthe directory from which you are trying to run this service.\n");
		}
	}

	/**
	 * Loads the list of the PDF files that need to be extracted (AKA, don't exist
	 * in the CSV file) into the pdfFileToBeExtractedList array.
	 */
	public void loadListOfPDFFilesToBeExtracted() {
		String row;
		pdfFileToBeExtractedList = new ArrayList<File>();
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(csvPath.getAbsolutePath()));
			ArrayList<File> filesThatExistInTheCSV = new ArrayList<File>();
			while ((row = csvReader.readLine()) != null) {
				String[] csvLineData = row.split(DELIMITER);

				// Check if the file specified in the CSV file exists in the directory.
				for (File pdfFile : pdfFileList) {
					if (pdfFile.getName().equals(csvLineData[0])) {
						filesThatExistInTheCSV.add(pdfFile);
					}
				}
			}
			csvReader.close();

			for (File pdfFile : pdfFileList) {
				pdfFileToBeExtractedList.add(pdfFile);
			}
			pdfFileToBeExtractedList.removeAll(filesThatExistInTheCSV);

		} catch (Exception e) {
			System.out.println("Could not find the CSV file, or could not read from said file.\n");
		}
	}

	/**
	 * Prints the list of files that require extraction.
	 */
	public void printListOfPDFFilesToBeExtracted() {
		for (File file : pdfFileToBeExtractedList) {
			System.out.println("File needs to be extracted: " + file.getName());
		}
	}

	/**
	 * Extracts all of the PDF file's metadata into the CSV file, using cermine's
	 * library. It separates different columns with the defined delimiter.
	 */
	public void extractPDFmetadataToCSV() {
		try {
			BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvPath.getAbsolutePath(), true));
			int counter = 0;
			boolean fileStartedEmpty = false;
			if (csvPath.length() == 0)
				fileStartedEmpty = true;
			for (File fileEntry : pdfFileToBeExtractedList) {
				Element metadataElement = this.extract(fileEntry);
				@SuppressWarnings("unchecked")
				List<Element> authors = metadataElement.getChild("front").getChild("article-meta")
						.getChild("contrib-group").getChildren();
				String metadataCSVFormat = fileEntry.getName() + DELIMITER
						+ metadataElement.getChild("front").getChild("article-meta").getChild("title-group")
								.getChildText("article-title")
						+ DELIMITER
						+ metadataElement.getChild("front").getChild("journal-meta").getChild("journal-title-group")
								.getChildText("journal-title")
						+ DELIMITER + metadataElement.getChild("front").getChild("article-meta").getChild("pub-date")
								.getChildText("year")
						+ DELIMITER + concatenateAuthors(authors);
				if (counter == 0 && fileStartedEmpty)
					csvWriter.append(metadataCSVFormat);
				else
					csvWriter.append("\n" + metadataCSVFormat);
				counter++;

			}
			csvWriter.close();
		} catch (Exception e) {
			System.out.println("Error while trying to extract PDF metadata.\n");
			e.printStackTrace();
		}
	}

	/**
	 * This method extracts the metadata from a given PDF file.
	 * 
	 * @param file File from which the metadata will be extracted.
	 * @return Returns an Element, which is a representation of the root xml tag of
	 *         the metadata.
	 */
	public Element extract(File file) {
		ContentExtractor extractor;
		Element result = null;
		try {
			extractor = new ContentExtractor();
			InputStream inputStream = new FileInputStream(file.getAbsolutePath());
			extractor.setPDF(inputStream);
			System.out.print("Extracting: " + file.getName() + ". This might take a few seconds...");
			result = extractor.getContentAsNLM();
			System.out.println("DONE.");
		} catch (AnalysisException e) {
			System.out.println("Something went wrong with the analysis of the PDF file.\n");
		} catch (FileNotFoundException e) {
			System.out.println("File was not found.");
		} catch (Exception e) {
			System.out.println("Something went wrong while trying to open the PDF file.\n");
		}
		return result;
	}

	/**
	 * This method is used to concatenate various XML author elements together, in
	 * order to present them as a single cell in a table.
	 * 
	 * @param authors XML Parent element to the authors elements.
	 * @return Concatenated string including all of the author's names with a "&" in
	 *         between them.
	 */
	public String concatenateAuthors(List<Element> authors) {
		String authorString = "";
		int count = 0;
		for (Element ele : authors) {
			if (ele.getChildText("string-name") != null) {
				if (count != 0)
					authorString += " & ";
				authorString += ele.getChildText("string-name");
				count++;
			}
		}
		return authorString;
	}

	public ArrayList<File> getPdfFileList() {
		return pdfFileList;
	}

	public File getCSVPath() {
		return csvPath;
	}

	public ArrayList<File> getPdfFileToBeExtractedList() {
		return pdfFileToBeExtractedList;
	}
}
