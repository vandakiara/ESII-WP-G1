import com.mailslurp.api.api.WaitForControllerApi;
import com.mailslurp.client.ApiException;
import com.mailslurp.models.Email;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests to check login and registration are working properly,
 * as well as permissions, and forms submission.
 *
 * @author Vanda Barata (vsfba1@iscte-iul.pt)
 */
public class LoginPermissionsAndFormsTest extends WebDriverSetup {

    /**
     * Performs a login with an admin user and confirms the admin page appears after login.
     */
    @Test
    @Story("Admin user logs in and confirms login is successfull by checking wp-admin bar on top")
    @Description("Login with admin user and confirmation of wp-admin bar")
    public void testLogin() {
        try {
            login("admin", "admin");

            // check for the presence of the wp-admin bar on top
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("wp-admin-bar-root-default")));

            logout();
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Login",
                    "The test checking if login with user \"admin\" is possible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }

    }

    /**
     * Tries to perform a login with an nonexistent user and checks for the error once it fails.
     */
    @Test
    @Story("Unregistered user tries to log in and fails")
    @Description("Failed login with unregistered user")
    public void testUnregisteredUserCantLogin() {
        try {
            login("randomUser", "pass");

            // check for the alert message that shows when an unregistered user tries to login.
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("user-registration-error")));
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Login with Unregistered User",
                    "The test checking if login with unregistered user is impossible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Confirms is user is able to register and get a confirmation email.
     */
    @Test
    @Story("User registers and receives confirmation email")
    @Description("User is able to register and get a confirmation email")
    public void testUserIsAbleToRegister() {
        try {
            final String email = inbox.getEmailAddress();

            register(email);

            // receive a verification email from wordpress using mailslurp
            WaitForControllerApi waitForControllerApi = new WaitForControllerApi(mailslurpClient);

            try {
                // wait for latest unread email in the inbox
                Email mail = waitForControllerApi.waitForLatestEmail(inbox.getId(), 10000L, true);

                // confirm the user received the registration email
                assertTrue(Objects.requireNonNull(mail.getSubject()).contains("Please confirm your registration on ESII-WP-G1"));

                // create a regex for matching the code we expect in the email body
                Pattern p = Pattern.compile("http\\S*?\\?\\S*");
                Matcher matcher = p.matcher(Objects.requireNonNull(mail.getBody()));

                assertTrue(matcher.find());

                String confirmationLink = matcher.group(0).toString();

                assert(confirmationLink.contains("ur_token"));
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Registering with User",
                    "The test checking if registering is possible has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Logs in with a "Member" user and then checks for
     * the presence of the menu item for the website Analytics.
     */
    @Test
    @Story("Member user logs in and has access to Website Analytics")
    @Description("Member user is able to login and have access to the Website Analytics tab")
    public void testMemberHasAccessToAnalytics() {
        try {
            login("member", "Member");
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("menu-item-222")));

            driver.findElement(By.cssSelector("#footer-col1 > aside > ul > li > a")).click();

            driver.findElement(By.cssSelector("#user-registration > div > p:nth-child(4) > a")).click();
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Member Has Access to Analytics",
                    "The test checking if a Member user has access to Website Analytics has failed. Here's what happened: \n\n" + e);
            fail(e);
        }

    }

    /**
     * Logs in with an "Administrator" user and then checks for
     * the presence of the menu item for the Covid Scientific Discoveries Repository.
     */
    @Test
    @Story("Administrator user logs in and has access to Covid Scientific Discoveries Repository")
    @Description("Administrator user is able to login and have access to the Covid Scientific Discoveries Repository tab")
    public void testAdminHasAccessToCovidRepo() {
        try {
            login("admin", "admin");
            driver.get(baseUrl);

            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("menu-item-267")));

            logout();
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for Administrator Has Access to Covid Scientific Discoveries Repository",
                    "The test checking if an Administrator user has access to Covid Scientific Discoveries Repository has failed. Here's what happened: \n\n" + e);
            fail(e);
        }
    }

    /**
     * Submits a contact us form and checks for the email having been sent.
     */
    @Test
    @Story("User sends an email through the Contact Us form and confirms the receipt on their own inbox")
    @Description("Form is submitted in the Contact Us page and the receipt is confirmed in the sender's inbox")
    public void testContactUsFormSubmission() {
        try {
            final String email = inbox.getEmailAddress();

            submitContactUsForm(email);

            // receive a submission confirmation email from wordpress using mailslurp
            WaitForControllerApi waitForControllerApi = new WaitForControllerApi(mailslurpClient);

            try {
                // wait for latest unread email in the inbox
                Email mail = waitForControllerApi.waitForLatestEmail(inbox.getId(), 10000L, true);

                // confirm the user received the form submission email
                assertTrue(Objects.requireNonNull(mail.getSubject()).contains("Submission Confirmation"));
            } catch (ApiException e) {
                e.printStackTrace();
                throw e;
            }
        } catch (Exception e) {
            sendNotificationEmailToWPAdmin(
                    "WP-CMS: Test failure for submitting a Contact Us form",
                    "The test checking if it's possible to submit a Contact Us form has failed. Here's what happened: \n\n" + e);
            fail(e);
        }

    }

    /**
     * Performs a login with given username and password.
     *
     * @param username  username to be used to login.
     * @param password  password to be used to login.
     */
    @Step("Login with user {0} and password {1}")
    private void login(String username, String password) throws Exception {
        try {
            driver.get(baseUrl);

            driver.findElement(By.id("menu-item-220")).findElement(By.tagName("a")).click();

            WebElement usernameField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

            WebElement passwordField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("password")));

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);

            driver.findElement(By.name("login")).click();
        } catch (Exception e) {
            throw new Exception("Logging in with user " + username + " and password " + password + " wasn't possible. Here's what happened: \n" + e);
        }

    }

    /**
     * Method to logout after having logged in.
     */
    @Step("Logout from admin session")
    private void logout() throws Exception {
        try {
            driver.get(baseUrl);

            driver.findElement(By.cssSelector("#footer-col1 > aside > ul > li > a")).click();

            driver.findElement(By.cssSelector("#user-registration > div > p:nth-child(4) > a")).click();

            WebElement loggedOutWarning = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#login > p.message")));

            assert(loggedOutWarning.getText().contains("You are now logged out"));
        } catch (Exception e) {
            throw new Exception("Logging out wasn't possible. Here's what happened: \n" + e);
        }
    }

    /**
     * Performs a registration with given email and password.
     *
     * @param email email to be used for registration.
     */
    @Step("Register new user with email {0}")
    private void register(String email) throws Exception {
        try {
            driver.get(baseUrl);

            driver.findElement(By.cssSelector("#footer-col1 > aside > ul > li.page_item.page-item-37 > a")).click();

            WebElement firstNameField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("first_name")));

            WebElement lastNameField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("last_name")));

            WebElement emailField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("user_email")));

            WebElement passwordField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("user_pass")));

            WebElement confirmationPassowrdField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("user_confirm_password")));

            WebElement privacyCheckButton = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("privacy_policy_1591487464")));

            WebElement submitButton = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user-registration-form-79\"]/form/div[5]/button")));

            firstNameField.sendKeys("TestName");

            lastNameField.sendKeys("TestLastName");

            emailField.sendKeys(email);

            passwordField.sendKeys("pass");

            confirmationPassowrdField.sendKeys("pass");

            privacyCheckButton.click();

            submitButton.click();

            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("ur-submit-message-node")));
        } catch (Exception e) {
            throw new Exception("Registering with user " + email + " wasn't possible. Here's what happened: \n" + e);
        }
    }

    /**
     * Submits a Contact Us form with given email.
     *
     * @param email email to be used for contacting the website admin.
     */
    @Step("Submit Contact Us form, from email {0}")
    private void submitContactUsForm(String email) throws Exception {
        try {
            driver.get(baseUrl);

            driver.findElement(By.cssSelector("#footer-col3 > aside > ul > li.page_item.page-item-35 > a")).click();

            WebElement nameField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("nf-field-1")));

            WebElement sujectField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("nf-field-5")));

            WebElement emailField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("nf-field-2")));

            WebElement messageField = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("nf-field-3")));

            WebElement submitButton = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("nf-field-4")));

            nameField.sendKeys("testName");

            sujectField.sendKeys("testSubject");

            emailField.sendKeys(email);

            messageField.sendKeys("testMessage");

            submitButton.click();

            // waits for the successful submit page to appear
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("post-35")));
        } catch (Exception e) {
            throw new Exception("Submitting Contact Us form from email " + email + " wasn't possible. Here's what happened: \n" + e);
        }

    }
}
