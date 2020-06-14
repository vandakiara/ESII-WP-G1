import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests about the git repositories overall health.
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class ReposHealthTest extends WebDriverSetup{

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
        try {
            checkRepoHealth(groupGitRepo);
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test faillure for Group Git Repo Health",
                    "The test checking the health for the group's github repository has failed. \n\n" + e);
            fail(e);
        }
    }

    /**
     * Checks if the teacher's github repo is accessible.
     */
    @Test
    @Story("Teacher's github respository is returning a 200 OK response")
    @Description("Teacher's github respository returns a 200 OK response")
    public void testTeachersRepoIsHealthy() {
        try {
            checkRepoHealth(teachersESIIGitRepo);
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test faillure for Teachers Git Repo Health",
                    "The test checking the health for the teacher's github repository has failed. \n\n" + e);
            fail(e);
        }
    }

    /**
     * Method to confirm that the git repo is healthy.
     * Makes a GET request and checks if the response is "200" (OK).
     *
     * @param url           the url for the git repo being checked.
     * @throws Exception    exception to trigger sending a notification email.
     */
    @Step("Check github repository {0} for a 200 OK response")
    private void checkRepoHealth(String url) throws Exception{
        // establish a http connection to the given url and extract the response code
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        final int responseCode = con.getResponseCode();

        if(responseCode == 200) {
            assertTrue(true);
        }
        else {
            throw new Exception("Response code wasn't the expected 200. It returned code " + responseCode + " instead");
        }
    }

}
