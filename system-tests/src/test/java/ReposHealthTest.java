import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
     */
    @Test
    @Story("Group's base github repository is returning a 200 OK response")
    @Description("Group's base github repository returns a 200 OK response")
    public void testGroupRepoIsHealthy() {
        checkRepoHealth(groupGitRepo);
    }

    /**
     * Checks if the teacher's github repo is accessible.
     */
    @Test
    @Story("Teacher's github respository is returning a 200 OK response")
    @Description("Teacher's github respository returns a 200 OK response")
    public void testTeachersRepoIsHealthy() {
        checkRepoHealth(teachersESIIGitRepo);
    }

    /**
     * Method to confirm that the git repo is healthy.
     * Makes a GET request and checks if the response is "200" (OK).
     *
     * @param url   The url for the git repo being checked.
     */
    @Step("Check github repository {0} for a 200 OK response")
    private void checkRepoHealth(String url) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            final int responseCode = con.getResponseCode();
            assertEquals(200, responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
