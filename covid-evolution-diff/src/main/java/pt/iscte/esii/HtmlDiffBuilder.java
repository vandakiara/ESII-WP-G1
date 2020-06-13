package pt.iscte.esii;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class HtmlDiffBuilder {

	/**
	 * Stores the latest HTML Page built
	 */
	private String htmlPage = "";

	/**
	 * Builds a Diff Page based on a GitDiff
	 * @param diff
	 * @return The HTML built based in the GitDiff
	 * @throws IOException
	 */
	public String buildDiffPage(GitDiff diff) throws IOException {
		File htmlTemplateFile = new File("assets/diff-template.html");

		htmlPage = FileUtils.readFileToString(htmlTemplateFile, "utf-8");
		htmlPage = htmlPage.replace("$tagBase", diff.getTagBase());
		htmlPage = htmlPage.replace("$tagCompare", diff.getTagCompare());

		String diffTableRows = getDiffTableRows(diff);
		htmlPage = htmlPage.replace("$diffRows", diffTableRows);
		return htmlPage;
	}

	/**
	 * @param diff
	 * @return The HTML Table with the diff content in the rows
	 */
	private String getDiffTableRows(GitDiff diff) {
		List<GitDiffChunk> diffChunks = diff.getChunks();
		String diffTableRows = "";
		for (GitDiffChunk chunk : diffChunks) {
			String deletionNumber = chunk.getType() == DiffType.DELETION || chunk.getType() == DiffType.NEUTRAL
					? Integer.toString(chunk.getLineDeletion())
					: "";
			String additionNumber = chunk.getType() == DiffType.ADDITION || chunk.getType() == DiffType.NEUTRAL
					? Integer.toString(chunk.getLineAddition())
					: "";
			String type = chunk.getType().getStr();
			String line = chunk.getLine().replaceAll("\\s", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;");
			diffTableRows += "<tr class=\"diff-" + type + "-row\">\n"
					+ "        <td class=\"diff-" + type + "-number\">" + deletionNumber + "</td>\n"
					+ "        <td class=\"diff-" + type + "-number\">" + additionNumber + "</td>\n"
					+ "        <td>" + line + "</td>\n"
					+ "      </tr>\n";
		}
		return diffTableRows;
	}

	/**
	 * @return Get previously built HTML Page
	 */
	public String getHtmlPage() {
		return htmlPage;
	}

}
