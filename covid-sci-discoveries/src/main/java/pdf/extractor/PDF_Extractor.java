package pdf.extractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.jdom.Element;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

public class PDF_Extractor {
	
	public Element extract() {
		ContentExtractor extractor;
		Element result = null;
		try {
			extractor = new ContentExtractor();
			InputStream inputStream = new FileInputStream("path/to/pdf/file");
			extractor.setPDF(inputStream);
			result = extractor.getContentAsNLM();
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went wrong with the analysis of the PDF file.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File was not found.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went wrong while trying to open the PDF file.");
		}
		return result;
	}
}
