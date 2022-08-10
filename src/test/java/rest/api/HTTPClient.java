package rest.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class HTTPClient {
    private static final String BASE_URI = System.getProperty("baseUrl", "https://st2016.inv.bg");
    private static final String BASE_PATH = System.getProperty("basePath", "/RESTapi");
    protected static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    public HTTPClient(AuthenticationScheme scheme) {
        RestAssured.authentication = scheme;
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
    }

    protected Response post(String resourceUrl, String body) {
        return baseRequest()
                .body(body)
                .post(resourceUrl)
                .prettyPeek();
    }

    protected Response put(String resourceUrl, String body) {
        return baseRequest()
                .body(body)
                .put(resourceUrl)
                .prettyPeek();
    }

    protected Response get(String resourceUrl) {
        return baseRequest()
                .get(resourceUrl)
                .prettyPeek();
    }

    protected Response delete(String resourceUrl) {
        return baseRequest()
                .delete(resourceUrl)
                .prettyPeek();
    }

    private RequestSpecification baseRequest() {
        return RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when();
    }
}
