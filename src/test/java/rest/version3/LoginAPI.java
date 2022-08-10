package rest.version3;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.response.Response;
import rest.dto.LoginResponse;
import rest.dto.TokenCredentials;

public class LoginAPI extends HTTPClientV3 {
    private static final String LOGIN_TOKEN_URL = "/login/token";

    public LoginAPI(AuthenticationScheme scheme) {
        super(scheme);
    }

    /**
     * Obtains bearer token
     * @param tokenCredentials credentials for the account
     * @return token
     */
    public String getToken(TokenCredentials tokenCredentials){
        Response response = post(LOGIN_TOKEN_URL, GSON.toJson(tokenCredentials));
        LoginResponse login = GSON.fromJson(response.body().asString(), LoginResponse.class);
        return login.getToken();
    }
}
