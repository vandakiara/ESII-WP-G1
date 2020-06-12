import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class WebDriverSetup {

    /**
     * The browser being used in the docker container.
     */
    public static WebDriver browser;

    /**
     * URL to be used for tests - the hostname defined in docker and ports for the wordpress container.
     */
    public static final String baseUrl = TestProperties.HOSTNAME + TestProperties.WP_PORT;

    /**
     * The container with the chrome browser and webdriver for Selenium.
     */
    @Container
    private static final BrowserWebDriverContainer BROWSER_CONTAINER =
            new BrowserWebDriverContainer().withCapabilities(new ChromeOptions());

    @BeforeAll
    public static void configureBrowser() {
        browser = BROWSER_CONTAINER.getWebDriver();
    }

}
