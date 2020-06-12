import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class ConnGit {

	final String REMOTE_URL = "https://github.com/vbasto-iscte/ESII1920.git";
	final String LOCAL_PATH = "ESII1920";
	final String FILE_PATH = "covid19spreading.rdf";
	private Repository repository;
	private Git git;
	private Ref tagLatest;
	private Ref tagPrevious;
	OutputStream output = new OutputStream() {
		private StringBuilder string = new StringBuilder();

		@Override
		public void write(int x) throws IOException {
			this.string.append((char) x);
		}

		public String toString() {
			return this.string.toString();
		}
	};

	public boolean hasNewTag() {
		Collection<Ref> refs;
		try {
			refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(REMOTE_URL).call();
			return refs.iterator().next().equals(tagLatest);
		} catch (GitAPIException e) {
			System.out.println("Failed to check for new tags in " + REMOTE_URL + ": " + e);
		}
		return false;
	}

	public GitDiff getDiff() throws IOException, InvalidRemoteException, TransportException, GitAPIException {

		/**
		 * Clear to local directory to clone the remote project
		 */
		File localPathFile = File.createTempFile(LOCAL_PATH, "");
		if (!localPathFile.delete()) {
			throw new IOException("Could not delete temporary file " + LOCAL_PATH);
		}
		/**
		 * Clone project locally
		 */
		git = Git.cloneRepository().setDirectory(localPathFile).setURI(REMOTE_URL).call();
		repository = git.getRepository();

		/**
		 * Get latest tags
		 */
		Object[] tagArray = git.tagList().call().toArray();
		int size = tagArray.length;
		Ref tagLatest = size > 0 ? (Ref) tagArray[size - 1] : null;
		Ref tagPrevious = size > 1 ? (Ref) tagArray[size - 2] : null;

		/**
		 * Prepare Tree Iterators to compare version from both tags
		 */
		AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, tagLatest.getObjectId().getName());
		AbstractTreeIterator newTreeParser = prepareTreeParser(repository, tagPrevious.getObjectId().getName());

		/**
		 * Get list of Diff Entries from comparing the file version between the 2 latest tags
		 */
		List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser)
				.setPathFilter(PathFilter.create(FILE_PATH)).
				call();

		/**
		 * Parse Diff Entries into Git Diff Chunks, that can later be used to generate
		 * the HTML file containing the Diff
		 */
		List<List<GitDiffChunk>> parsedDiff = null;
		for (DiffEntry entry : diff) {

			try (DiffFormatter formatter = new DiffFormatter(output)) {
				formatter.setRepository(repository);
				formatter.format(entry);
				parsedDiff = getDiffChunks(output.toString());
			}
		}

		git.close();
		String tagBase = tagPrevious.getName().replaceAll("refs/tags/", "");
		String tagCompare = tagLatest.getName().replaceAll("refs/tags/", "");
		return new GitDiff(tagBase, tagCompare, parsedDiff);
	}

	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
		/**
		 *  From the tag we can, build the tree which allows to construct the
		 *  TreeParser for doing the diff
		 */
		try (RevWalk walk = new RevWalk(repository)) {
			RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
			RevTree tree = walk.parseTree(commit.getTree().getId());

			CanonicalTreeParser treeParser = new CanonicalTreeParser();
			try (ObjectReader reader = repository.newObjectReader()) {
				treeParser.reset(reader, tree.getId());
			}

			walk.dispose();

			return treeParser;
		}
	}

	public List<List<GitDiffChunk>> getDiffChunks(String diff) {
		List<List<GitDiffChunk>> chunks = new ArrayList<List<GitDiffChunk>>();
		String[] diffStrs = diff.split("\n@@");
		if (diffStrs.length < 2) {
			return chunks;
		}

		for (int i = 1; i < diffStrs.length; i++) {
			String diffBlock = diffStrs[i];
			String[] lines = diffBlock.split("\n");
			if (lines.length < 2) {
				continue;
			}
			List<GitDiffChunk> chunk = new ArrayList<GitDiffChunk>();
			int startDeleted = Integer.parseInt(lines[0].substring(lines[0].indexOf('-') + 1, lines[0].indexOf(',')));
			int startAddition = Integer.parseInt(
					lines[0].substring(lines[0].indexOf('+') + 1, lines[0].indexOf(',', lines[0].indexOf(',') + 1)));
			int countDeleted = startDeleted;
			int countAddition = startAddition;

			for (int j = 1; j < lines.length; j++) {
				String d = lines[j];
				DiffType type = d.indexOf('-') == 0 ? DiffType.DELETION
						: d.indexOf('+') == 0 ? DiffType.ADDITION : DiffType.NEUTRAL;
				chunk.add(new GitDiffChunk(countAddition, countDeleted, type, d));

				switch (type) {
				case DELETION:
					countDeleted++;
					break;
				case ADDITION:
					countAddition++;
					break;
				default:
					countDeleted++;
					countAddition++;
					break;
				}
			}
			chunks.add(chunk);
		}

		return chunks;
	}

}
