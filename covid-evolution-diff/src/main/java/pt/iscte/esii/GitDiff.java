package pt.iscte.esii;
import java.util.List;

public class GitDiff {
	/**
	 * The Base Tag of the Diff
	 */
	private String tagBase;
	/**
	 * The Latest Tag to be compared against the Base Tag
	 */
	private String tagCompare;
	/**
	 * The List of GitDiffChunk 
	 */
	private List<GitDiffChunk> chunks;

	/**
	 * GitDiffChunk Contructor
	 * @param tagBase
	 * @param tagCompare
	 * @param parsedDiff
	 */
	public GitDiff(String tagBase, String tagCompare, List<GitDiffChunk> parsedDiff) {
		super();
		this.tagBase = tagBase;
		this.tagCompare = tagCompare;
		this.chunks = parsedDiff;
	}

	/**
	 * @return Tha Base Tag
	 */
	public String getTagBase() {
		return tagBase;
	}

	/**
	 * @return The Comparing Tag
	 */
	public String getTagCompare() {
		return tagCompare;
	}

	/**
	 * @return The List of GitDiffChunk for this Diff file
	 */
	public List<GitDiffChunk> getChunks() {
		return chunks;
	}
	
	
}
