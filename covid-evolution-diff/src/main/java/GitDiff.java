import java.util.List;

public class GitDiff {
	String tagBase;
	String tagCompare;
	List<List<GitDiffChunk>> chunks;

	public GitDiff(String tagBase, String tagCompare, List<List<GitDiffChunk>> chunks) {
		super();
		this.tagBase = tagBase;
		this.tagCompare = tagCompare;
		this.chunks = chunks;
	}
}
