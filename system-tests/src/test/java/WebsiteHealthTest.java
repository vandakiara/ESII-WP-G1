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
        goToMenuPageAndCheckTitle("menu-item-56", "Covid Evolution");
    }

    /**
     * Confirms the Covid Spread page is being loaded by checking its title.
     */
    @Test
    public void covidSpreadReturnsOK() {
        goToMenuPageAndCheckTitle("menu-item-57", "Covid Spread");
    }

    /**
     * Confirms the Covid Queries page is being loaded by checking its title.
     */
    @Test
    public void covidQueriesReturnsOK() {
        goToMenuPageAndCheckTitle("menu-item-55", "Covid Queries");
    }

    /**
     * Confirms the Covid Wiki page is being loaded by checking its title.
     */
    @Test
    public void covidWikiReturnsOK() {
        goToMenuPageAndCheckTitle("menu-item-203", "Covid-19");
    }

    /**
     * Confirms the Covid Scientific Discoveries page is being loaded by checking its title.
     */
    @Test
    public void covidScientificReturnsOK() {
        goToMenuPageAndCheckTitle("menu-item-58", "Covid Scientific Discoveries");
    }

    /**
     * Confirms the About Us page is being loaded by checking its title.
     */
    @Test
    public void aboutUsReturnsOK() {
        goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-39 > a", "About Us");
    }

    /**
     * Confirms the Contact Us page is being loaded by checking its title.
     */
    @Test
    public void contactUsReturnsOK() {
        goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-35 > a", "Contact Us");
    }

    /**
     * Confirms the Covid FAQ page is being loaded by checking its title.
     */
    @Test
    public void covidFAQReturnsOK() {
        goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-32 > a", "Covid FAQ");
    }

    /**
     * Confirms the Privacy Policy page is being loaded by checking its title.
     */
    @Test
    public void privacyPolicyReturnsOK() {
        goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-239 > a", "Privacy Policy");
    }

    /**
     * Method that validates if a page is loading by selecting it from the main page,
     * and then verifying if it's loaded by checking its title.
     *
     * @param menuId        the id that identifies the menu item.
     * @param titleCheck    the title we want to compare against to validate the test.
     */
    private void goToMenuPageAndCheckTitle(String menuId, String titleCheck) {
        driver.get(baseUrl);

        driver.findElement(By.id(menuId)).findElement(By.tagName("a")).click();

        WebElement covidPageTitle = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#intro-core > h1 > span")));

        assert(covidPageTitle.getAttribute("innerHTML").contains(titleCheck));
    }

    /**
     * Method that validates if a page is loading by selecting it from the main page,
     * and then verifying if it's loaded by checking its title.
     *
     * @param cssSelect     the css selector that identifies the item.
     * @param titleCheck    the title we want to compare against to validate the test.
     */
    private void goToFooterPageAndCheckTitle(String cssSelect, String titleCheck) {
        driver.get(baseUrl);

        driver.findElement(By.cssSelector(cssSelect)).click();

        WebElement covidPageTitle = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#intro-core > h1 > span")));

        assert(covidPageTitle.getAttribute("innerHTML").contains(titleCheck));
    }

}
