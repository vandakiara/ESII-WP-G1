package pt.iscte.esii;
public class GitDiffChunk {
	/**
	 * The current line number for addition
	 */
	private int lineAddition;
	/**
	 * The current line number for deletion
	 */
	private int lineDeletion;
	/**
	 * The DiffType on this diff chunk
	 */
	private DiffType type;
	/**
	 * The file line content for this diff chunk 
	 */
	private String line;

	/**
	 * GitDiffChunk contructor
	 * @param lineAddition
	 * @param lineDeletion
	 * @param type
	 * @param line
	 */
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

	/**
	 * @return The current line number for addition 
	 */
	public int getLineAddition() {
		return lineAddition;
	}

	/**
	 * @return The current line number for deletion
	 */
	public int getLineDeletion() {
		return lineDeletion;
	}

	/**
	 * @return The DiffType on this diff chunk
	 */
	public DiffType getType() {
		return type;
	}

	/**
	 * @return The file line content for this diff chunk 
	 */
	public String getLine() {
		return line;
	}

	
}
