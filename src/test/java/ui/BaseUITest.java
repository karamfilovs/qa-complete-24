package ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.restassured.authentication.NoAuthScheme;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import org.junit.jupiter.api.*;
import rest.dto.TokenCredentials;
import rest.version3.ClientV3API;
import rest.version3.LoginAPI;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseUITest {
    private static final String EMAIL = "karamfilovs@gmail.com";
    private static final String PASSWORD = "123456";
    protected ClientV3API clientAPI = null;
    private final LoginAPI loginAPI = new LoginAPI(new NoAuthScheme());
    protected Page page = null;
    protected Browser browser = Playwright
            .create()
            .chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1));

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test:" + testInfo.getDisplayName());
        page = browser.newPage();
        page.navigate("https://st2016.inv.bg");
    }

    @AfterEach
    void afterEach() {
        if (page != null) {
            page.close();
        }
    }

    @BeforeAll
    void beforeAll() {
        //Retrieve bearer token
        TokenCredentials credentials = new TokenCredentials("st2016", EMAIL, PASSWORD);
        String bearerToken = loginAPI.getToken(credentials);

        //Create OAuth3 authentication scheme
        PreemptiveOAuth2HeaderScheme scheme = new PreemptiveOAuth2HeaderScheme();
        scheme.setAccessToken(bearerToken);
        clientAPI = new ClientV3API(scheme);
    }
}
