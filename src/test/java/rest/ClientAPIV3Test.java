package rest;

import io.restassured.authentication.NoAuthScheme;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import org.junit.jupiter.api.*;
import rest.dto.Client;
import rest.dto.TokenCredentials;
import rest.version3.ClientV3API;
import rest.version3.LoginAPI;

import java.util.Locale;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientAPIV3Test {
    private static final String EMAIL = "karamfilovs@gmail.com";
    private static final String PASSWORD = "123456";
    private ClientV3API clientAPI = null;
    private final LoginAPI loginAPI = new LoginAPI(new NoAuthScheme());

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

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: " + testInfo.getDisplayName().toUpperCase(Locale.ROOT));

    }

    @Test
    @DisplayName("Can get all clients v3")
    void canGetAllClients() {
        clientAPI.getAllClients();
    }


    @Test
    @DisplayName("Can get client v3")
    void canGetClient() {
        clientAPI.getClient("1629");
    }

    @Test
    @DisplayName("Can create client v3")
    void canCreateClient() {
        clientAPI.createClient(Client.builder()
                .name("BTV")
                .address("Sofia")
                .town("Sofia")
                .bulstat("202202202")
                .mol("Ivan Ivanov")
                .build());
    }
}
