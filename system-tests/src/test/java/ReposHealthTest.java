import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests about the git repositories overall health.
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class ReposHealthTest {

    /**
     * Git repository used by our group for the project.
     */
    private static final String groupGitRepo = "https://github.com/vandakiara/ESII-WP-G1";

    /**
     * Git repository made by the subject teachers, containing important files for our website to work.
     */
    private static final String teachersESIIGitRepo = "https://github.com/vbasto-iscte/ESII1920";

    /**
     * Checks if the group github repo is accessible.
     *
     * @throws IOException
     */
    @Test
    public void groupRepoIsHealthy() throws IOException {
        checkRepoHealth(groupGitRepo);
    }

    /**
     * Checks if the teacher's github repo is accessible.
     *
     * @throws IOException
     */
    @Test
    public void teachersRepoIsHealthy() throws IOException {
        checkRepoHealth(teachersESIIGitRepo);
    }

    /**
     * Method to confirm that the git repo is healthy.
     * Makes a GET request and checks if the response is "200" (OK).
     *
     * @param url           The url for the git repo being checked.
     * @throws IOException
     */
    private void checkRepoHealth(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        final int responseCode = con.getResponseCode();

        assertEquals(200, responseCode);
    }

}
