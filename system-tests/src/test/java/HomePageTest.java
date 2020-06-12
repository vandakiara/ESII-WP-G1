import org.junit.jupiter.api.Test;

public class HomePageTest extends WebDriverSetup {

    @Test
    public void homepageReturnsOK() {
        browser.get("http://www.google.com");

        String title = browser.getTitle();
        System.out.println(title);

        // assert(browser.getTitle().contains("ESII-WP-G1"));
    }


}
