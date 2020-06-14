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
     * Port used for the covid-graph-spread docker container.
     */
    public static final int COVID_SPREAD_PORT = 3001;

    /**
     * Port used for the covid-sci-discoveries docker container.
     */
    public static final int COVID_SCI_DISCOVERIES_PORT = 3002;

    /**
     * Port used for the covid-query docker container.
     */
    public static final int COVID_QUERY_PORT = 3003;

    /**
     * Email address of the WP-CMS administrator.
     */
    public static final String WP_ADMIN_MAIL = "es2wpg1@gmail.com";

}
