
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class ConnectGit {

	final String REMOTE_URL = "https://github.com/vbasto-iscte/ESII1920.git";
	final String LOCAL_PATH = "ESII1920";
	final String FILE_PATH = "covid19spreading.rdf";
	Repository repository;
	Git git;
	File covFile;


	public void cloneRepo() throws InvalidRemoteException, TransportException, GitAPIException {
		File localPathFile = null;

		try {
			localPathFile = File.createTempFile(LOCAL_PATH, "");
			if (!localPathFile.delete()) 
				throw new IOException("Could not delete temporary file " + LOCAL_PATH);	

			git = Git.cloneRepository().setDirectory(localPathFile).setURI(REMOTE_URL).call();

			repository = git.getRepository();
		}		
		catch (Exception e) {
			System.out.println("cenas");
		}

		try {

			// find the HEAD
			ObjectId lastCommitId = repository.resolve(Constants.HEAD);

			RevWalk revWalk = new RevWalk(repository);
			RevCommit commit = revWalk.parseCommit(lastCommitId);
			// and using commit's tree find the path
			RevTree tree = commit.getTree();
			

			// now try to find a specific file
			try {
				TreeWalk treeWalk = new TreeWalk(repository);
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(PathFilter.create("covid19spreading.rdf"));
				if (!treeWalk.next()) {
					throw new IllegalStateException("Did not find expected file 'covid19spreading.rdf'");
				}

				ObjectId objectId = treeWalk.getObjectId(0);
				ObjectLoader loader = repository.open(objectId);
				
				// and then one can the loader to read the file
				File myfile = new File(FILE_PATH);
				FileOutputStream stream = new FileOutputStream(myfile);
		        loader.copyTo(stream);		        
		        covFile = myfile;
					
			}catch (Exception e) {
				// TODO: handle exception
			}

			revWalk.dispose();
		}catch (Exception e) {
			// TODO: handle exception
		}

		git.close();

	}
	
	public File getCovFile() {
		return covFile;
	}

}
