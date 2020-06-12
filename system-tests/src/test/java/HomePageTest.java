import org.junit.jupiter.api.Test;

public class HomePageTest extends WebDriverSetup {

    @Test
    public void homepageReturnsOK() {
        driver.get(baseUrl);
        assert(driver.getTitle().contains("ESII-WP-G1"));
    }


}
