import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class HtmlDiffBuilder {

	private String htmlPage = "";
	
	public String buildDiffPage(GitDiff diff) throws IOException {
		File htmlTemplateFile = new File("assets/diff-template.html");
		
		htmlPage = FileUtils.readFileToString(htmlTemplateFile, "utf-8");
		htmlPage = htmlPage.replace("$tagBase", diff.tagBase);
		htmlPage = htmlPage.replace("$tagCompare", diff.tagCompare);

		List<GitDiffChunk> diffChunks = diff.chunks;
		String diffTableRows = "";
		for (GitDiffChunk chunk : diffChunks) {
			String deletionNumber = chunk.type == DiffType.DELETION || chunk.type == DiffType.NEUTRAL ? Integer.toString(chunk.lineDeletion) : "";
			String additionNumber = chunk.type == DiffType.ADDITION || chunk.type == DiffType.NEUTRAL ? Integer.toString(chunk.lineAddition) : "";
			String type = chunk.type.getStr();
			String line = chunk.line.replaceAll("\\s", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;");
			diffTableRows += "<tr class=\"diff-"+ type +"-row\">\n" + 
					"        <td class=\"diff-"+ type +"-number\">"+deletionNumber+"</td>\n" + 
					"        <td class=\"diff-"+ type +"-number\">"+additionNumber+"</td>\n" + 
					"        <td>"+ line + "</td>\n" + 
					"      </tr>\n";
		}

		htmlPage = htmlPage.replace("$diffRows", diffTableRows);
		return htmlPage;
	}
	
	public String getHtmlPage() {
		return htmlPage;
	}

}
