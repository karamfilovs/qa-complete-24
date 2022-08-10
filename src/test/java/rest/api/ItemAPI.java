package rest.api;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.response.Response;
import rest.dto.Item;

public class ItemAPI extends HTTPClient {
    private static final String ITEM_URL = "/item";
    private static final String ITEMS_URL = "/items";

    public ItemAPI(AuthenticationScheme scheme) {
        super(scheme);
    }


    public Response createItem(Item item) {
        return post(ITEM_URL, GSON.toJson(item));
    }

    public Response updateItem(String id, Item item) {
        return put(ITEM_URL + "/" + id, GSON.toJson(item));
    }

    public Response getAllItems() {
        return get(ITEMS_URL);
    }

    public Response getItem(String id) {
        return get(ITEM_URL + "/" + id);
    }

    public Response deleteItem(String id) {
        return delete(ITEM_URL + "/" + id);
    }
}
