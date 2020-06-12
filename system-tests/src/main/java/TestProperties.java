import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Properties to be used for the tests.
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class TestProperties {

    /**
     * Private constructor to avoid instantiation.
     */
    private TestProperties() {
    }

    /**
     * Hostname for the machine where the dockers are running.
     */
    public static final String HOSTNAME = "localhost";

    /**
     * Port used for the wordpress docker container.
     */
    public static final int WP_PORT = 8080;

    /**
     * Port used for the covid-evolution-diff docker container.
     */
    public static final int COVID_DIFF_PORT = 3000;

    /**
     * Service name for the wordpress container.
     */
    public static final String WP_SERVICE_NAME = "wordpress";

    /**
     * Service name for the covid-evolution-diff app docker container.
     */
    public static final String COVID_DIFF_SERVICE_NAME = "java-covid-evolution-diff";

    /**
     * Docker compose files used to launch the System Test environment.
     */
    private static final List<File> dockerComposeFiles = new ArrayList<>();

    /**
     * Docker compose file used to launch the whole wordpress docker environment.
     */
    private static final File DOCKER_COMPOSE_FILE= new File(System.getProperty("user.dir") + "/../docker-compose.yml");

    /**
     * Gets the necessary docker-compose files.
     *
     * @return the list with the used docker-compose files.
     */
    public static List<File> getDockerComposeFiles() {
        if (dockerComposeFiles.isEmpty()) {
            dockerComposeFiles.add(DOCKER_COMPOSE_FILE);
        }
        return dockerComposeFiles;
    }



}
