import com.mailslurp.api.api.InboxControllerApi;
import com.mailslurp.client.ApiClient;
import com.mailslurp.client.ApiException;
import com.mailslurp.models.Inbox;
import com.mailslurp.models.SendEmailOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.Objects;

import static com.mailslurp.client.Configuration.getDefaultApiClient;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The setup for selenium and the chrome webdriver.
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class WebDriverSetup {

    /**
     * The browser being used in the docker container.
     */
    public static WebDriver driver;

    /**
     * URL to be used for tests - the localhost and the port for the wordpress container.
     */
    public static final String baseUrl = "http://" + TestProperties.HOSTNAME + ":" + TestProperties.WP_PORT;

    /**
     * The WP-CMS administrator's email address.
     */
    private static final String adminEmail = TestProperties.WP_ADMIN_MAIL;

    /**
     * The mailslurp client which will allow testing registrations and whatever else requires the use of emails.
     */
    public static ApiClient mailslurpClient;

    /**
     * Inbox to be used with the random generated email.
     */
    public static Inbox inbox;

    /**
     * The mailslurp API controller.
     */
    public static InboxControllerApi inboxControllerApi;


    /**
     * Method to do the needed setup for selenium and the webdriver, with the needed options.
     */
    @BeforeAll
    public static void setUp() {
        // Chromedriver version 81 - only compatible with linux and chrome version 81.
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // to run without graphical mode

        // solution to avoid tests failing due to problems with the chromedriver
        // taken from https://stackoverflow.com/a/52340526
        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc

        driver = new ChromeDriver(options);

        // setup mailslurp
        mailslurpClient = getDefaultApiClient();
        mailslurpClient.setApiKey("90145077217b5625ca5b9f3e964ae5eae2816144ec264139d698be13b1cb1ea0");
        mailslurpClient.setConnectTimeout(100000);

        // create a real, randomized email address with MailSlurp to represent a user
        inboxControllerApi = new InboxControllerApi(mailslurpClient);

        try {
            // create random email with inbox
            inbox = inboxControllerApi.createInbox("testInbox",null,null,null, "testInbox", null);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        // check if the inbox was created
        assertNotNull(inbox.getId());
        assertTrue(Objects.requireNonNull(inbox.getEmailAddress()).contains("@mailslurp.com"));
    }

    /**
     * Method to ensure the browser is closed at the end of the tests.
     */
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    /**
     * Sends a notification email to the WP admin.
     *
     * @param subject   the email subject to be sent.
     * @param message   the mail body to be sent.
     */
    public static void sendNotificationEmailToWPAdmin(String subject, String message) {
            SendEmailOptions emailOptions = new SendEmailOptions();

        emailOptions.to(Collections.singletonList(adminEmail));
        emailOptions.subject(subject);
        emailOptions.body(message);

        try {

            System.out.println("Sending notification email...");
            inboxControllerApi.sendEmail(inbox.getId(), emailOptions);

        } catch (ApiException e) {
            System.err.println("Exception when calling InboxControllerApi#sendEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

    }

}
