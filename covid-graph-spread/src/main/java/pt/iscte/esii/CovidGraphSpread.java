package pt.iscte.esii;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

/** 
 * @author Diego Souza
 * @version 1.1
 * @since 1.0
*/

/**
 * The CovidGraphSpread will be used to retrieve all information from the repository
 * This class connects to Git,
 * Accesses the covid19spreading.rdf file available in the repository,
 * Retrieves all covid19spreading.rdf file versions that have tags associated,
 * and builds a html table.
 */
public class CovidGraphSpread {

	/** Represents the address repository.*/
	static String REMOTE_URL = "https://github.com/vbasto-iscte/ESII1920.git";
	
	/** Represents the spread visualization link standard.*/
	String link = "http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf";
	
	/** Represents the local path where the repository will be cloned.*/
	final String LOCAL_PATH = "ESII1920";
	
	/** Represents the file name to filter.*/
	final String FILE_PATH = "covid19spreading.rdf";
	
	/** Represents the revision walker for the repository.*/
	private RevWalk walk;
	
	/** Represents the repository.*/
	private Repository repository;
	
	/** Represents the Git version control system.*/
	private Git git;
	
	/** Represents the all file versions that have tags associated.*/
	private static ArrayList<DataTable> rows;
	
	/** Represents the tree walker for the repository.*/
	private TreeWalk treeWalk;
	
	/** Variable to iterate the tag list.*/
	private int id;

	/** Variable to store the latest tag .*/
	private Ref latestTag = null;
	
	/**
	 * This main method connects to Git,
	 * Accesses the covid19spreading.rdf file available in the repository,
	 * Retrieves all covid19spreading.rdf file versions that have tags associated.
	 */
	public void connectionGit() {
		id = 0;
		rows = new ArrayList<DataTable>();
		connection();
		fileVersions();
		git.close();
	}
	
	/**
	 * This method connects to Git and clones the repository temporarily.
	 * @exception IOException if stream to file cannot be written to or closed.
	 * @exception GitAPIException if it has error thrown by Git API commands.
	 */
	public void connection() {
		File localPathFile = null;
		try {
			localPathFile = File.createTempFile(LOCAL_PATH, "");
			if (!localPathFile.delete()) {
				throw new IOException("Could not delete temporary file " + LOCAL_PATH);
			}
			git = Git.cloneRepository().setDirectory(localPathFile).setURI(REMOTE_URL).call();
			repository = git.getRepository();
		} catch (GitAPIException | IOException e) {
			System.out.println("Failed to read repo at " + REMOTE_URL + ": " + e);
		}
	}

	/**
	 * Accesses the covid19spreading.rdf in the repository,
	 * Retrieves all covid19spreading.rdf file versions,
	 * and stores each file version in a DataTable object.
	 * @exception IOException if stream to file cannot be written to or closed.
	 * @exception GitAPIException if it has error thrown by Git API commands.
	 */
	public void fileVersions(){
		
		 List<Ref> tagList;
		try {
			tagList = git.tagList().call();
			int size = tagList.size();
			latestTag = size > 0 ? (Ref) tagList.get(size-1) : null;
	        for (Ref ref : tagList) {
	        	try {
	        		walk = new RevWalk(repository);
					RevCommit commitTag = walk.parseCommit(tagList.get(id).getObjectId());
			        RevTree tree = commitTag.getTree();
					treeWalk = new TreeWalk(repository);
					treeWalk.setRecursive(false);
					treeWalk.addTree(tree);
					treeWalk.setFilter(PathFilter.create("covid19spreading.rdf"));
					
					while (treeWalk.next()) {
					    if (treeWalk.isSubtree()) {
					        System.out.println("dir: " + treeWalk.getPathString());
					        treeWalk.enterSubtree();
					    } else {
					    	String tagName = ref.getName().replace("refs/tags/", "");
					    	String spreadVisualizationLink = link.replace("master", tagName);
					    	DataTable dataFileVersion = new DataTable(new Date(commitTag.getCommitTime() * 1000L).toString(),treeWalk.getPathString(),
					    	tagName, commitTag.getShortMessage(), spreadVisualizationLink);
					    	rows.add(dataFileVersion);
			                id = id + 1;
					    }
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        }
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method builds a html table from list of all file versions.
	 * @return this html table.
	 */
	public String buildTableHtml() {
		HtmlCovidTableBuilder page = new HtmlCovidTableBuilder();
		page.buildPage(rows);
		System.out.println(page.getHtmlPage());
		return page.getHtmlPage();
	}

	/**
	 * Check, without cloning the project, if there is a new Tag in the remote repository
	 * @return true if there is new tag, otherwise false
	 */
	public boolean hasNewTag() {
		if (latestTag == null) {
			return true;
		}
		Collection<Ref> refs;
		try {
			refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(REMOTE_URL).call();
			return !refs.iterator().next().getObjectId().equals(latestTag.getObjectId());
		} catch (GitAPIException e) {
			System.out.println("Failed to check for new tags in " + REMOTE_URL + ": " + e);
		}
		return false;
	}
}
