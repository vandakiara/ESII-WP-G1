import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests about the website overall health ie if all the pages load correctly.
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class WebsiteHealthTest extends WebDriverSetup {

    /**
     * Confirms the homepage is loading correctly by checking its title.
     */
    @Test
    public void homepageReturnsOK() {
        driver.get(baseUrl);
        assert(driver.getTitle().contains("ESII-WP-G1"));
    }

    /**
     * Confirms the Covid Evolution page is being loaded by checking its title.
     */
    @Test
    public void covidEvolutionReturnsOK() {
        goToPageAndCheckTitle("menu-item-56", "Covid Evolution");
    }

    /**
     * Confirms the Covid Spread page is being loaded by checking its title.
     */
    @Test
    public void covidSpreadReturnsOK() {
        goToPageAndCheckTitle("menu-item-57", "Covid Spread");
    }

    /**
     * Confirms the Covid Queries page is being loaded by checking its title.
     */
    @Test
    public void covidQueriesReturnsOK() {
        goToPageAndCheckTitle("menu-item-55", "Covid Queries");
    }

    /**
     * Confirms the Covid Wiki page is being loaded by checking its title.
     */
    @Test
    public void covidWikiReturnsOK() {
        goToPageAndCheckTitle("menu-item-203", "Covid-19");
    }

    /**
     * Confirms the Covid Scientific Discoveries page is being loaded by checking its title.
     */
    @Test
    public void covidScientificReturnsOK() {
        goToPageAndCheckTitle("menu-item-58", "Covid Scientific Discoveries");
    }

    private void goToPageAndCheckTitle(String menuId, String titleCheck) {
        driver.get(baseUrl);

        driver.findElement(By.id(menuId)).findElement(By.tagName("a")).click();

        WebElement covidPageTitle = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#intro-core > h1 > span")));

        assert(covidPageTitle.getAttribute("innerHTML").contains(titleCheck));
    }

}
