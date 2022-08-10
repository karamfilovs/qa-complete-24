package rest.api;

import io.restassured.authentication.AuthenticationScheme;

public class ClientAPI extends HTTPClient{
    public ClientAPI(AuthenticationScheme scheme) {
        super(scheme);
    }
}
