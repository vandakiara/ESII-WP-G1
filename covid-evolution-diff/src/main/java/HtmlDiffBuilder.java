import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class HtmlDiffBuilder {
	
	public String buildDiffPage(GitDiff diff) {
		File htmlTemplateFile = new File("assets/diff-template.html");
		
		String htmlString = null;
		try {
			htmlString = FileUtils.readFileToString(htmlTemplateFile, "utf-8");
			htmlString = htmlString.replace("$tagBase", diff.tagBase);
			htmlString = htmlString.replace("$tagCompare", diff.tagCompare);

			List<List<GitDiffChunk>> diffChunks = diff.chunks;
			String diffTableRows = "";
			for (List<GitDiffChunk> chunks : diffChunks) {
				diffTableRows += "<tr class=\"diff-info-row\">\n" + 
						"        <td class=\"diff-info-number\"></td>\n" + 
						"        <td class=\"diff-info-number\"></td>\n" + 
						"        <td ></td>\n" + 
						"      </tr>\n";
				for (GitDiffChunk chunk : chunks) {
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
			}

			htmlString = htmlString.replace("$diffRows", diffTableRows);
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlString;
	}

}
