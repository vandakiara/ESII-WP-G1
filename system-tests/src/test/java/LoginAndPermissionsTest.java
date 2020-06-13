import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginAndPermissionsTest extends WebDriverSetup {

    /**
     * Performs a login with an admin user and confirms the admin page appears after login.
     */
    @Test
    public void loginTest() {
        login("admin", "admin");

        WebElement adminBar = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("wp-admin-bar-root-default")));
    }

    /**
     * Tries to perform a login with an nonexistent user and checks for the error once it fails.
     */
    @Test
    public void unregisteredUserCantLoginTest() {
        login("randomUser", "pass");

        WebElement userLoginError = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.className("user-registration-error")));
    }

    @Test
    public void memberHasAccessToAnalytics() {

    }

    /**
     * Performs a login with given username and password.
     *
     * @param username  username to be used to login.
     * @param password  password to be used to login.
     */
    private void login(String username, String password) {
        driver.get(baseUrl);

        driver.findElement(By.id("menu-item-220")).findElement(By.tagName("a")).click();

        WebElement usernameField = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        WebElement passwordField = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("password")));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        driver.findElement(By.name("login")).click();
    }

    private void register(String username, String password) {
        driver.get(baseUrl);

        driver.findElement(By.id("menu-item-220")).findElement(By.tagName("a")).click();


    }
}
