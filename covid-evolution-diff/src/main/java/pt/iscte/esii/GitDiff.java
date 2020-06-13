package pt.iscte.esii;
import java.util.List;

public class GitDiff {
	String tagBase;
	String tagCompare;
	List<GitDiffChunk> chunks;

	public GitDiff(String tagBase, String tagCompare, List<GitDiffChunk> parsedDiff) {
		super();
		this.tagBase = tagBase;
		this.tagCompare = tagCompare;
		this.chunks = parsedDiff;
	}
}
