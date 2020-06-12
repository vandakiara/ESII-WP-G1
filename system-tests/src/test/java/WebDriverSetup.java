import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.dockerclient.DockerClientConfigUtils;
import org.testcontainers.dockerclient.LogToStringContainerCallback;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import java.util.Optional;

@Testcontainers
public class WebDriverSetup {

    /**
     * The browser being used in the docker container.
     */
    public static WebDriver driver;

    /**
     * URL to be used for tests - the localhost (equivalent to the host's IP) and the port for the wordpress container.
     */
    public static final String baseUrl = "http://" + getDetectedDockerHostIp() + ":" + TestProperties.WP_PORT;

    /**
     * The container with the chrome browser and webdriver for Selenium.
     */
    @Container
    public static final BrowserWebDriverContainer BROWSER_CONTAINER =
            new BrowserWebDriverContainer("selenium/standalone-chrome-debug").withCapabilities(new ChromeOptions());

    @BeforeAll
    public static void configureBrowser() {
        driver = BROWSER_CONTAINER.getWebDriver();
    }

    /**
     * Taken from {@link DockerClientConfigUtils#getDetectedDockerHostIp()} but removed
     * {@link DockerClientConfigUtils#IN_A_CONTAINER} because this always reports false.
     *
     * @return the ip address for the browser to connect to.
     */
    private static String getDetectedDockerHostIp() {
        return Optional.ofNullable(DockerClientFactory.instance().runInsideDocker(
                cmd -> cmd.withCmd("sh", "-c", "ip route|awk '/default/ { print $3 }'"),
                (client, id) -> {
                    try {
                        return client.logContainerCmd(id)
                                .withStdOut(true)
                                .exec(new LogToStringContainerCallback())
                                .toString();
                    } catch (Exception e) {
                        return null;
                    }
                }
        ))
                .map(StringUtils::trimToEmpty)
                .filter(StringUtils::isNotBlank)
                .orElse(null);
    }

}
