package ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;
import ui.pages.LoginPage;

public class LoginPageTest {
    private LoginPage loginPage = null;
    private Page page = null;
    private Browser browser = Playwright
            .create()
            .chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test:" + testInfo.getDisplayName());
        page = browser.newPage();
        page.navigate("https://st2016.inv.bg");
        loginPage = new LoginPage(page);
    }

    @AfterEach
    void afterEach() {
        if (page != null) {
            page.close();
        }
    }

    @Test
    @DisplayName("Can login with valid credentials - v2")
    void canLoginWithValidCredentialsV2() {
        loginPage.defaultLogin();
        Assertions.assertEquals("karamfilovs@gmail.com", loginPage.getLoggerUser());
    }

    @Test
    @DisplayName("Can login with valid credentials")
    void canLoginWithValidCredentials() {
        //Enter email/password and click Login button
        page.fill("#loginusername", "karamfilovs@gmail.com" );
        page.fill("#loginpassword", "123456");
        page.click("//input[@class='selenium-submit-button g-recaptcha']");
        String loggedUser = page.textContent("//div[@class='userpanel-header']").trim();
        Assertions.assertEquals("karamfilovs@gmail.com", loggedUser);
    }


    @Test
    @DisplayName("Cant login with invalid credentials")
    void cantLoginWithInvalidCredentials() {
        page.fill("#loginusername", "karamfilovs@gmail.com" );
        page.fill("#loginpassword", "12345678");
        page.click("//input[@class='selenium-submit-button g-recaptcha']");
        String actualMessage = page.textContent("//div[@class='selenium-error-block']");
        String expectedErrorMessage = "Грешно потребителско име или парола. Моля, опитайте отново.";
        Assertions.assertTrue(actualMessage.contains(expectedErrorMessage), "Error message is not OK");
    }

    @Test
    @DisplayName("Cant login with blank credentials")
    void cantLoginWithBlankCredentials() {
        page.click("//input[@class='selenium-submit-button g-recaptcha']");
        String actualMessage = page.textContent("//div[@class='selenium-error-block']");
        String expectedErrorMessage = "Моля, попълнете вашия email";
        Assertions.assertTrue(actualMessage.contains(expectedErrorMessage), "Error message is not OK");
    }
}
