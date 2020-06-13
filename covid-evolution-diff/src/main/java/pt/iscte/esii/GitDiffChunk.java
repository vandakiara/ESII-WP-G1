package pt.iscte.esii;
public class GitDiffChunk {
	int lineAddition;
	int lineDeletion;
	DiffType type;
	String line;

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

}
