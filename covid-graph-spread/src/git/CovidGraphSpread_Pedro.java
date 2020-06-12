package git;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;


public class CovidGraphSpread_Pedro {
	File localRepository = new File("git/");
	List<ObjectId> filesList = new ArrayList<ObjectId>();
	HashMap<ObjectId, ArrayList<String>> values = new HashMap<ObjectId, ArrayList<String>>();
	Git git;

	public void cloneGit() {
		try {
			if (localRepository.exists()) {
				git = Git.open(localRepository);
				git.fetch().call();
			} else {
				git = Git.cloneRepository().setURI("https://github.com/vbasto-iscte/ESII1920.git")
						.setDirectory(localRepository).setCloneAllBranches(true).call();
			}
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getRDFfile() {
		try {
			System.out.println(git.getRepository());
			RevWalk revWalk = new RevWalk(git.getRepository());

			/*
			 * System.out.println("Walk through " + revWalk.toString() + " with files ");
			 * for (RevCommit commit : revWalk) { System.out.println("Commits in tree");
			 * System.out.println(commit.getFullMessage()); } Iterable <RevCommit> logs =
			 * git.log().call(); Iterator <RevCommit> i = logs.iterator(); RevCommit commit
			 * = null; while (i.hasNext()) { commit = revWalk.parseCommit(i.next());
			 * System.out.println(commit.getFullMessage());
			 * //System.out.println("Tree files: " + treeId.getName()); // TreeWalk tree =
			 * TreeWalk.forPath(git.getRepository(), "b/.rdf", commit.getTree()); //
			 * ObjectId object = tree.getObjectId(0); // ObjectLoader loader =
			 * git.getRepository().open(object); // loader.copyTo(System.out); }
			 */
			// TreeWalk tree = TreeWalk.forPath(git.getRepository(), ".rdf",
			// commit.getTree());

			/*
			 * RevWalk walk = new RevWalk(git.getRepository()); ObjectId obj =
			 * git.getRepository().resolve("HEAD^{tree}");
			 * 
			 * ObjectLoader load = git.getRepository().open(obj); load.copyTo(System.out);
			 */
			// byte[] bytes = load.getBytes();
			// String data = new String (bytes, "utf-8");
			// System.out.println(data);

			List<Ref> call = git.tagList().call();
			for (Ref ref : call) {
				// filesList.add(ref.getObjectId());
				System.out.println("Tag " + ref + " " + ref.getName() + " " + ref.getObjectId());
				if (ref.getObjectId() instanceof ObjectId) {
					git.checkout().setName(ref.getName()).call();
					System.out.println("Checking commit " + ref.getName());
					ObjectId lastcommit = git.getRepository().resolve(Constants.HEAD);
					RevWalk walk = new RevWalk(git.getRepository());
					RevCommit commit = walk.parseCommit(lastcommit);
					RevTree tree = commit.getTree();

					TreeWalk tw = new TreeWalk(git.getRepository());
					tw.addTree(tree);
					tw.setRecursive(true);
					tw.setFilter(PathFilter.create("covid19spreading.rdf"));

					if (tw.next()) {
						ObjectId obj = tw.getObjectId(0);
						// ObjectId file = ObjectId.fromString(obj.getName());
						filesList.add(obj);
						values.put(obj, valuesList(new Date(commit.getCommitTime() * 1000L).toString(),
								tw.getPathString(), ref.getName(), commit.getFullMessage(), null));

						System.out.println("\nFound file " + tw.getPathString() + " created on "
								+ new Date(commit.getCommitTime() * 1000L));

						ObjectLoader load = git.getRepository().open(obj);
						load.copyTo(System.out);
					}

					tw.close();
					walk.close();
				}
				// ObjectId object = ref.getObjectId();
				// ObjectLoader loader = git.getRepository().open(object);
				// loader.copyTo(System.out);
				// load.copyTo(System.out);
				// System.out.println(file.getName());
			}

			git.clean().call();
			git.close();
			// File covidFile = new File(repository.getDirectory().getParent(),
			// "covid19spreading.rdf");
		} catch (RefAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RefNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidRefNameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CheckoutConflictException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MissingObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> valuesList(String timeStamp, String fileName, String fileTag, String tagDescription,
			String link) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(timeStamp);
		list.add(fileName);
		list.add(fileTag);
		list.add(tagDescription);
		list.add(link);
		return list;

	}

	public void populateHTML() {
		System.out.println(filesList.toString());
//		out.write("<html><body><table border=\"1\"><thead>" +
//		        "<tr><th>File TimeStamp</th><th>File Name</th>" +
//		        "<th>File Tag</th><th>Tag Description</th>"+
//		        "<th>Spread Visualization Link</th></tr></thead><tbody>");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter("test.html"));
			pw.write("<html><body><table border='1'><thead>" + "<tr><th>File TimeStamp</th><th>File Name</th><th>File Tag</th>"
					+ "<th>Tag Description</th><th>Spread Visualization Link</th></tr>" + "</thead></body>");
			// values.forEach((k,v) -> System.out.println("key: "+ k + " values: " + v );;
			for (Entry<ObjectId, ArrayList<String>> h : values.entrySet()) {
				pw.write("<tr>");
				for (String s : h.getValue()) {
					pw.write("<td>" + s + "</td>");
				}
				pw.write("</tr>");
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("Hello World!");

		CovidGraphSpread_Pedro app = new CovidGraphSpread_Pedro();
		app.cloneGit();
		app.getRDFfile();
		app.populateHTML();

	}
	
	
}
