package github;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class CovidGraphSpread {

	static String REMOTE_URL = "https://github.com/vbasto-iscte/ESII1920.git";
	String link = "http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf";
	final String LOCAL_PATH = "ESII1920";
	final String FILE_PATH = "covid19spreading.rdf";
	private RevWalk walk;
	private Repository repository;
	private Git git;
	private ArrayList<DataTable> row = new ArrayList<DataTable>();
	private int id = 0;
	private TreeWalk treeWalk;

	
	
	public void connectionGit() throws GitAPIException {
		connection();
		tagList();
		buildTableHTML(row);
		git.close();
	}

	private void tagList() throws GitAPIException {
		//List All tags
		 List<Ref> tagList = git.tagList().call();
	        for (Ref ref : tagList) {
	        	try {
	        		walk = new RevWalk(repository);
					RevCommit commitTag2 = walk.parseCommit(tagList.get(id).getObjectId());
			        RevTree tree = commitTag2.getTree();
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
					    	DataTable data = new DataTable(commitTag2.getAuthorIdent().getWhen(),treeWalk.getPathString(),
					    	tagName, commitTag2.getShortMessage(), spreadVisualizationLink);
					    	row.add(data);
					    	/*System.out.println("id: " + id);
			                System.out.println("File timestamp: " + commitTag2.getAuthorIdent().getWhen());
			                System.out.println("File name: " + treeWalk.getPathString());
			                System.out.println("File tag: " + tagName);
			                System.out.println("Tag Description: " + commitTag2.getShortMessage());
			                System.out.println("Spread Visualization Link: " + spreadVisualizationLink);
			                System.out.println("---------------------------------------------------------------------");*/
			                id = id + 1;
					    }
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	}

	private String buildTableHTML(ArrayList<DataTable> row) {
		// TODO Auto-generated method stub
		String table = "<table><tr><th>File timestamp</th><th>File Name</th><th>File Tag</th><th>File Description</th><th>Spread Visualization Link</th></tr>";
		for (DataTable l : row) {
			String line = "<tr><th>" + l.getFileTimeStamp() + "</th><th>"+ l.getFileName() + "</th><th>" + l.getFileTag() + "</th><th>" +
					l.getTagDescription() + "</th><th> <a href='" + l.getSpreadVisualizationLink()+ "'>"+l.getSpreadVisualizationLink()+"</a></th></tr>";
			table = table.concat(line);
		}
		table = table.concat("</table>");
		System.out.println(table);
		return table;
	}

	private void connection() {
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
	
	public static void main(String[] args) throws GitAPIException {
		// TODO Auto-generated method stub
		CovidGraphSpread covidGraphSpread = new CovidGraphSpread();
		covidGraphSpread.connectionGit();
	}

}
