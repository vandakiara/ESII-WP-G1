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


}
