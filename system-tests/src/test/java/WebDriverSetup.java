import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * The setup for selenium and the chrome webdriver.
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
@Testcontainers
public class WebDriverSetup {

    /**
     * The browser being used in the docker container.
     */
    public static WebDriver driver;

    /**
     * URL to be used for tests - the localhost (equivalent to the host's IP) and the port for the wordpress container.
     */
    public static final String baseUrl = "http://" + TestProperties.HOSTNAME + ":" + TestProperties.WP_PORT;

    @BeforeAll
    public static void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // to run without graphical mode
        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc

        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
