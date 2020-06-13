import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Tests about the website overall health ie if all the pages load correctly.
 */
public class WebsiteHealthTest extends WebDriverSetup {

    @Test
    public void homepageReturnsOK() {
        driver.get(baseUrl);
        assert(driver.getTitle().contains("ESII-WP-G1"));
    }

    @Test
    public void covidEvolutionReturnsOK() {
        driver.get(baseUrl);

        driver.findElement(By.id("menu-item-56")).findElement(By.tagName("a")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement covidPageTitle = driver.findElement(By.cssSelector("#intro-core > h1 > span"));

        assert(covidPageTitle.getAttribute("innerHTML").contains("Covid Evolution"));
    }


}
