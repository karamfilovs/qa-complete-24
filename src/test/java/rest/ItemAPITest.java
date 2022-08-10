package rest;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import rest.api.ItemAPI;
import rest.dto.Item;
import rest.dto.ItemResp;
import rest.dto.SuccessResponse;

import java.time.LocalDateTime;
import java.util.Locale;

public class ItemAPITest {
    private static final Gson GSON = new GsonBuilder().create();
    private static final String EMAIL = "karamfilovs@gmail.com";
    private static final String PASSWORD = "123456";
    private ItemAPI itemAPI = null;
    private static final Faker faker = new Faker(Locale.UK);

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: " + testInfo.getDisplayName().toUpperCase(Locale.ROOT));
        PreemptiveBasicAuthScheme scheme = new PreemptiveBasicAuthScheme();
        scheme.setUserName(EMAIL);
        scheme.setPassword(PASSWORD);
        itemAPI = new ItemAPI(scheme);
    }

    @Test
    @DisplayName("Can create item")
    void createItem() {
        Item item = new Item("CreateItemTest_" + LocalDateTime.now().getNano(), "kg.", 10.50);
        Response createResp = itemAPI.createItem(item);
        Assertions.assertEquals(200, createResp.statusCode());
        //Get the item id from the response
        SuccessResponse successResponse = GSON.fromJson(createResp.body().asString(), SuccessResponse.class);
        Assertions.assertEquals("Артикула е създаден успешно!", successResponse.getSuccess().getMessage());
        Assertions.assertFalse(successResponse.getSuccess().getId().isEmpty());
        //Get item
        Response getResp = itemAPI.getItem(successResponse.getSuccess().getId());
        Assertions.assertEquals(200, getResp.statusCode());
        //Deserialize item json to item object
        ItemResp itemResp = GSON.fromJson(getResp.body().asString(), ItemResp.class);
        //Check item fields for correctness
        Assertions.assertEquals(item.getName(), itemResp.getItem().getName());
        Assertions.assertEquals(item.getQuantity_unit(), itemResp.getItem().getQuantity_unit());
    }

    @Test
    @DisplayName("Can delete item")
    void canDeleteItem() {
        //Create item
        Item item = new Item(faker.address().cityName(), "kg.", faker.beer().hashCode());
        Response createResp = itemAPI.createItem(item);
        Assertions.assertEquals(200, createResp.statusCode());
        //Get the item id from the response
        SuccessResponse successResponse = GSON.fromJson(createResp.body().asString(), SuccessResponse.class);
        Assertions.assertEquals("Артикула е създаден успешно!", successResponse.getSuccess().getMessage());
        Assertions.assertFalse(successResponse.getSuccess().getId().isEmpty());
        //Retrieve item id
        String itemId = successResponse.getSuccess().getId();
        //Delete item
        Response deleteResp = itemAPI.deleteItem(itemId);
        SuccessResponse deleteSuccess = GSON.fromJson(deleteResp.body().asString(), SuccessResponse.class);
        Assertions.assertEquals("Артикула е изтрит", deleteSuccess.getSuccess().getMessage());
        //Check that the item is gone
        //Response getResp = itemAPI.getItem(itemId); //TODO: See next line
        //Assertions.assertEquals(404, getResp.statusCode()); //TODO: Uncomment when bug INV-54 is fixed
        //Get all items and make sure the item is gone
        Response getAllResp = itemAPI.getAllItems();
        Assertions.assertFalse(getAllResp.body().asString().contains(item.getName()));
    }


    @Test
    @Tag("api")
    @DisplayName("Can update existing item")
    void canUpdateItem() {
        //Create item
        Item item = new Item(faker.address().cityName(), "kg.", faker.beer().hashCode());
        Response createResp = itemAPI.createItem(item);
        Assertions.assertEquals(200, createResp.statusCode());
        //Get the item id from the response
        SuccessResponse successResponse = GSON.fromJson(createResp.body().asString(), SuccessResponse.class);
        Assertions.assertEquals("Артикула е създаден успешно!", successResponse.getSuccess().getMessage());
        Assertions.assertFalse(successResponse.getSuccess().getId().isEmpty());
        //Retrieve item id
        String itemId = successResponse.getSuccess().getId();
        //Check item is created
        Response getResp = itemAPI.getItem(itemId);
        Assertions.assertEquals(200, getResp.statusCode());
        //Update item
        item.setName("Update Name");
        Response updateResp = itemAPI.updateItem(itemId, item);
        Assertions.assertEquals(200, updateResp.statusCode());
        //Get the item after update
        Response getAfterUpdateResp = itemAPI.getItem(itemId);
        Assertions.assertEquals(200, getAfterUpdateResp.statusCode());
        ItemResp itemResp = GSON.fromJson(getAfterUpdateResp.body().asString(), ItemResp.class);
        //Check that the item name is updated for real
        Assertions.assertEquals(item.getName(), itemResp.getItem().getName());
    }


    @Test
    @DisplayName("Can get all items - hard way")
    void canGetAllItemsHardWay() {
        RestAssured.baseURI = "https://st2016.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .basic(EMAIL, PASSWORD)
                .when()
                .get("/items")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Can get all items (incl. deleted) - hard way")
    void canGetAllItemsIncludingDeletedHardWay() {
        RestAssured.baseURI = "https://st2016.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.given()
                .log().all()
                .queryParam("with_deleted", true)
                .queryParam("limit", 5)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .basic(EMAIL, PASSWORD)
                .when()
                .get("/items")
                .prettyPeek()
                .then()
                .statusCode(200);
    }


    @Test
    @DisplayName("Can get all clients - hard way")
    void canGetAllClientsHardWay() {
        RestAssured.baseURI = "https://st2016.inv.bg";
        RestAssured.basePath = "/RESTapi";
        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .basic(EMAIL, PASSWORD)
                .when()
                .get("/clients")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}
