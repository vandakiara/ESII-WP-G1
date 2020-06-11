import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEnvironment {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(TestEnvironment.class);

    /**
     * Instantiation of the Docker Compose Environment using Test Containers.
     */
    public static DockerSetupEnvironment environment = new DockerSetupEnvironment(TestProperties.getDockerComposeFiles());

    public static String wordpressHost;

    static {
        // This static block will be executed only once, the first time you make an object of that class or the first
        // time you access a static member of that class (even if you never make an object of that class).
        // By doing this we will be able to launch the docker environment and instantiate the objects we need.
        try {
            logger.info("Waiting for environment to start");
            environment.start();
            logger.info("Getting wordpress hostname and ports");
            wordpressHost = environment.getServiceHost(TestProperties.WP_SERVICE_NAME, TestProperties.WP_PORT);
        } catch (final Throwable t) {
            throw new RuntimeException("There was an error while starting the environment!", t);
        }
    }
}
