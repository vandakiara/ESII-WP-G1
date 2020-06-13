package pdf.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

//This class is used in order to extract the metadata of every PDF file in a specified directory.
public class PDF_Extractor {
	File directoryPath;
	ArrayList<File> pdfFileList;
	ArrayList<Element> metadataList;
	public static final String PATH = "D:\\University\\ES II\\PDFs";
	public ArrayList<File> previousPDFs;
	public ArrayList<File> currentPDFs;

	// PDF Extractor Constructor
	public PDF_Extractor(String directoryPath) {
		System.out.println("Creating a PDF Extractor object...");
		this.directoryPath = new File(directoryPath);
		this.pdfFileList = new ArrayList<File>();
		this.metadataList = new ArrayList<Element>();
		System.out.println("PDF Extractor object was created successfully.");

		loadPreviousPDFList();
		populateLists();
		createCurrentPDFList();
	}

	public void populateLists() {
		populateFilesList();
		populateElementList();
	}

	public void populateFilesList() {
		System.out.println("Populating PDF file list...");

		for (File fileEntry : directoryPath.listFiles()) {
			if (fileEntry.exists() && !fileEntry.isDirectory() && fileEntry.getName().endsWith(".pdf")) {
				pdfFileList.add(fileEntry);
				System.out.println(fileEntry.getName() + " was succesfully added to the list.");
			}
		}

		removeDuplicatesPDFList();

		System.out.println("PDF file list is complete.");
	}

	public void populateElementList() {
		System.out.println("Starting to extract the metadata...");
		for (File fileEntry : pdfFileList) {
			if (fileEntry.getName().endsWith(".pdf")) {
				metadataList.add(this.extract(fileEntry));
			} else {
				System.out.println("Skipping over " + fileEntry.getName());
			}

		}
		System.out.println("Metadata was extracted sucessfully!");
	}

	// Removes duplicate PDF files. Requires the previousPDFs list to have been
	// previously populated.
	public void removeDuplicatesPDFList() {
		if (previousPDFs != null) {
			System.out.println("Ignoring already loaded PDFs...");
			ArrayList<File> tempArray = new ArrayList<File>();

			for (File file : pdfFileList) {
				for (File previousPDF : previousPDFs) {
					if (file.equals(previousPDF)) {
						tempArray.add(file);
					}
				}
			}
			pdfFileList.removeAll(tempArray);
		} else {
			System.out.println("Loading all PDF files.");
		}
	}

	// Loads the previousPDFs list with the paths in the control.txt file.
	public void loadPreviousPDFList() {
		String controlPath = PATH.concat("\\control.txt");
		BufferedReader in;

		try {
			in = new BufferedReader(new FileReader(controlPath));
			String str;
			if (previousPDFs == null) {
				previousPDFs = new ArrayList<File>();
			}
			while ((str = in.readLine()) != null) {
				previousPDFs.add(new File(str));
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File not created yet. Reloading all PDF files. " + controlPath);
			previousPDFs = null;
			return;
		} catch (IOException e) {
			System.out.println("Problem while reading control file;");
			e.printStackTrace();
		}
	}

	//
	public void createCurrentPDFList() {
		System.out.println("Creating current PDF list...");
		currentPDFs = pdfFileList;
		savePreviousPDFList();
		System.out.println("Current PDF list created.");
	}

	public void savePreviousPDFList() {
		if (currentPDFs != null) {
			File tmpDir = new File(PATH + "\\control.txt");
			if (!tmpDir.exists()) {
				try {
					tmpDir.createNewFile();
				} catch (IOException e) {
					System.out.println("Couldnt create a file at the following directory:\n" + tmpDir);
					e.printStackTrace();
				}
			}

			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileOutputStream(tmpDir));
				for (File file : currentPDFs)
					pw.println(file.getAbsolutePath());
				pw.close();
			} catch (FileNotFoundException e) {
				System.out.println("File not found for saving.");
				e.printStackTrace();
			}
		}else{
			System.out.println("No previous files found.");
		}
	}

	public Element extract(File file) {
		ContentExtractor extractor;
		Element result = null;
		System.out.println("Commencing extractiong for: " + file.getName());
		try {
			extractor = new ContentExtractor();
			InputStream inputStream = new FileInputStream(file.getAbsolutePath());
			extractor.setPDF(inputStream);
			System.out.println("Extracting: " + file.getName());
			System.out.println("This might take a few seconds...");
			result = extractor.getContentAsNLM();
			System.out.println("File extracted: " + file.getName());
		} catch (AnalysisException e) {
			e.printStackTrace();
			System.out.println("Something went wrong with the analysis of the PDF file.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File was not found.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Something went wrong while trying to open the PDF file.");
		}
		return result;
	}

	public String[][] getTable() {
		if (metadataList.size() == 0)
			return null;
		// Article title, Journal name, Publication year and Authors
		String[][] table = new String[4][metadataList.size() + 1];
		table[0][0] = "Article title";
		table[1][0] = "Journal name";
		table[2][0] = "Publication year";
		table[3][0] = "Authors";

		int count = 1;
		for (Element ele : metadataList) {
			table[0][count] = ele.getChild("front").getChild("article-meta").getChild("title-group")
					.getChildText("article-title");
			table[1][count] = ele.getChild("front").getChild("journal-meta").getChild("journal-title-group")
					.getChildText("journal-title");
			table[2][count] = ele.getChild("front").getChild("article-meta").getChild("pub-date").getChildText("year");
			@SuppressWarnings("unchecked")
			List<Element> authors = ele.getChild("front").getChild("article-meta").getChild("contrib-group")
					.getChildren();
			table[3][count] = concatenateAuthors(authors);
			count++;
		}
		return table;
	}

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

	public void printAllXML() {
		System.out.println("Starting to print all XML information...");
		for (Element ele : metadataList) {
			System.out.println(ele.getChild("front").getChild("journal-meta").getChild("journal-title-group")
					.getChildText("journal-title"));
		}
	}

	public ArrayList<File> getPdfFileList() {
		return pdfFileList;
	}

}
