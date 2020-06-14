import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
    @Story("Unregistered user opens website homepage and it's returned correctly")
    @Description("Homepage is correctly returned for anyone accessing it")
    public void testHomepageReturnsOK() {
        try {
            driver.get(baseUrl);
            if(driver.getTitle().contains("ESII-WP-G1")) {
                assertTrue(true);
            } else {
                throw new Exception("It wasn't possible to access the website's homepage at " + baseUrl);
            }
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Homepage availability",
                    "The test checking if the homepage is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Covid Evolution page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Covid Evolution page and it's returned correctly")
    @Description("Covid Evolution page is correctly returned for anyone accessing it")
    public void testCovidEvolutionReturnsOK() {
        try {
            goToMenuPageAndCheckTitle("menu-item-56", "Covid Evolution");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Covid Evolution page availability",
                    "The test checking if the Covid Evolution page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Covid Spread page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Covid Spread page and it's returned correctly")
    @Description("Covid Spread page is correctly returned for anyone accessing it")
    public void testCovidSpreadReturnsOK() {
        try {
            goToMenuPageAndCheckTitle("menu-item-57", "Covid Spread");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Covid Spread page availability",
                    "The test checking if the Covid Spread page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Covid Queries page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Covid Queries page and it's returned correctly")
    @Description("Covid Queries page is correctly returned for anyone accessing it")
    public void testCovidQueriesReturnsOK() {
        try {
            goToMenuPageAndCheckTitle("menu-item-55", "Covid Queries");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Covid Queries page availability",
                    "The test checking if the Covid Queries page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Covid Wiki page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Covid Wiki page and it's returned correctly")
    @Description("Covid Wiki page is correctly returned for anyone accessing it")
    public void testCovidWikiReturnsOK() {
        try {
            goToMenuPageAndCheckTitle("menu-item-203", "Covid-19");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Covid Wiki page availability",
                    "The test checking if the Covid Wiki page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Covid Scientific Discoveries page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Covid Scientific Discoveries page and it's returned correctly")
    @Description("Covid Scientific Discoveries page is correctly returned for anyone accessing it")
    public void testCovidScientificReturnsOK() {
        try {
            goToMenuPageAndCheckTitle("menu-item-58", "Covid Scientific Discoveries");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Covid Scientifc Discoveries page availability",
                    "The test checking if the Covid Scientific Discoveries page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the About Us page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website About Us page and it's returned correctly")
    @Description("About Us page is correctly returned for anyone accessing it")
    public void testAboutUsReturnsOK() {
        try {
            goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-39 > a", "About Us");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for About Us page availability",
                    "The test checking if the About Us page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Contact Us page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Contact Us page and it's returned correctly")
    @Description("Contact Us page is correctly returned for anyone accessing it")
    public void testContactUsReturnsOK() {
        try {
            goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-35 > a", "Contact Us");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Contact Us page availability",
                    "The test checking if the Contact Us page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Covid FAQ page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Covid FAQ page and it's returned correctly")
    @Description("Covid FAQ page is correctly returned for anyone accessing it")
    public void testCovidFAQReturnsOK() {
        try {
            goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-32 > a", "Covid FAQ");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Covid FAQ page availability",
                    "The test checking if the Covid FAQ page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms the Privacy Policy page is being loaded by checking its title.
     */
    @Test
    @Story("Unregistered user opens website Privacy Policy page and it's returned correctly")
    @Description("Privacy Policy page is correctly returned for anyone accessing it")
    public void testPrivacyPolicyReturnsOK() {
        try {
            goToFooterPageAndCheckTitle("#footer-col3 > aside > ul > li.page_item.page-item-239 > a", "Privacy Policy");
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Privacy Policy page availability",
                    "The test checking if the Privacy Policy page is accessible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Method that validates if a page is loading by selecting it from the main page,
     * and then verifying if it's loaded by checking its title.
     *
     * @param menuId        the id that identifies the menu item.
     * @param titleCheck    the title we want to compare against to validate the test.
     */
    @Step("Find menu page with id {0} and check for title {1}")
    private void goToMenuPageAndCheckTitle(String menuId, String titleCheck) throws Exception {
        try {
            driver.get(baseUrl);

            driver.findElement(By.id(menuId)).findElement(By.tagName("a")).click();

            WebElement covidPageTitle = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#intro-core > h1 > span")));

            if (covidPageTitle.getAttribute("innerHTML").contains(titleCheck)) {
                assertTrue(true);
            } else {
                throw new Exception("Title didn't match between what was expected (" + titleCheck + ") and what was found (" + covidPageTitle.getAttribute("innerHTML") + ").");
            }
        } catch (Exception e) {
            throw new Exception("It wasn't possible to find the intended element identified by " + menuId + ", or the expected page title (" + titleCheck + ") didn't match. Here's what happened: \n\n" + e);
        }
    }

    /**
     * Method that validates if a page is loading by selecting it from the main page,
     * and then verifying if it's loaded by checking its title.
     *
     * @param cssSelect     the css selector that identifies the item.
     * @param titleCheck    the title we want to compare against to validate the test.
     */
    @Step("Find footer page with cssSelector {0} and check for title {1}")
    private void goToFooterPageAndCheckTitle(String cssSelect, String titleCheck) throws Exception {
        try {
            driver.get(baseUrl);

            driver.findElement(By.cssSelector(cssSelect)).click();

            WebElement covidPageTitle = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#intro-core > h1 > span")));

            if (covidPageTitle.getAttribute("innerHTML").contains(titleCheck)) {
                assertTrue(true);
            } else {
                throw new Exception("Title didn't match between what was expected (" + titleCheck + ") and what was found (" + covidPageTitle.getAttribute("innerHTML") + ").");
            }
        } catch (Exception e) {
                throw new Exception("It wasn't possible to find the intended element identified by " + cssSelect + ", or the expected page title (" + titleCheck + ") didn't match. Here's what happened: \n\n" + e);
        }
    }

}
