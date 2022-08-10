package rest.api;

import io.restassured.authentication.AuthenticationScheme;

public class BankAccountAPI extends HTTPClient{
    public BankAccountAPI(AuthenticationScheme scheme) {
        super(scheme);
    }
}
