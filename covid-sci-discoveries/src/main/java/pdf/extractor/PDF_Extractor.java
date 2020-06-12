package pdf.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.jdom.Element;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

//This class is used in order to extract the metadata of every PDF file in a specified directory.
public class PDF_Extractor {
	File directoryPath;
	ArrayList<File> pdfFileList;
	ArrayList<Element> metadataList;

	public PDF_Extractor(String directoryPath) {
		System.out.println("++++++++++++++++++++++++++++++");
		System.out.println("Creating a PDF Extractor object...");
		this.directoryPath = new File(directoryPath);
		this.pdfFileList = new ArrayList<File>();
		this.metadataList = new ArrayList<Element>();
		System.out.println("PDF Extractor object was created successfully.");
		System.out.println("++++++++++++++++++++++++++++++\n");
	}

	public void populateFilesList() {
		System.out.println("++++++++++++++++++++++++++++++");
		System.out.println("Populating PDF file list...");
		for (File fileEntry : directoryPath.listFiles()) {
			System.out.println(fileEntry.getName() + " was succesfully added to the list.");
			if (fileEntry.exists() && !fileEntry.isDirectory()) {
				pdfFileList.add(fileEntry);
			}
		}
		System.out.println("PDF file list is complete.");
		System.out.println("++++++++++++++++++++++++++++++\n");
	}
	
	public void populateElementList() {
		System.out.println("++++++++++++++++++++++++++++++");
		System.out.println("Starting to extract the metadata...");
		for (File fileEntry : pdfFileList) {
			metadataList.add(this.extract(fileEntry));
		}
		System.out.println("Metadata was extracted sucessfully!");
		System.out.println("++++++++++++++++++++++++++++++\n");
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
	
	public void printAllXML() {
		for (Element ele : metadataList) {
			System.out.println("Starting to print all XML information... \n");
			System.out.println(ele.getChild("front")
					.getChild("journal-meta")
					.getChild("journal-title-group")
					.getChildText("journal-title"));
			
		}
	}
	
}
