import java.util.ArrayList;
import java.util.List;

public class GitDiffChunk {
	int lineAddition;
	int lineDeletion;
	DiffType type;
	String line;
//	List<GitDiffLineContent> line = new ArrayList<GitDiffLineContent>();

	public GitDiffChunk(int lineAddition, int lineDeletion, DiffType type, String line) {
		super();
		this.lineAddition = lineAddition;
		this.lineDeletion = lineDeletion;
		this.type = type;
		this.line = line;
	}

	@Override
	public String toString() {

		return "Line Addition: " + lineAddition + " | Line Deletion: " + lineDeletion + " | Type: " + type + " | Line: "
				+ line;
	}

//	public void addContent(boolean isDiff, String content) {
//		line.add(new GitDiffLineContent(isDiff, content));
//	}
}

//class GitDiffLineContent {
//	boolean isDiff;
//	String content;
//
//	public GitDiffLineContent(boolean isDiff, String content) {
//		super();
//		this.isDiff = isDiff;
//		this.content = content;
//	}
//}