package rest.version3;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.response.Response;
import rest.dto.AllClientResponse;
import rest.dto.Client;

import java.util.List;

public class ClientV3API extends HTTPClientV3 {
    private static final String CLIENT_URL = "/clients";

    public ClientV3API(AuthenticationScheme scheme) {
        super(scheme);
    }

    public Response createClient(Client client){
        return post(CLIENT_URL, GSON.toJson(client));
    }

    public Response getClient(String id){
        return get(CLIENT_URL + "/" + id);
    }

    public Response getAllClients(){
        return get(CLIENT_URL);
    }

    public void deleteAll(){
        Response getAllResp = getAllClients();
        AllClientResponse allClients = GSON.fromJson(getAllResp.body().asString(), AllClientResponse.class);
        List<Client> clients = allClients.getClients();
        clients.forEach(client -> System.out.println(client.getId()));
        clients.forEach(client -> deleteClient(client.getId()));
    }

    public Response deleteClient(String id){
        return delete(CLIENT_URL + "/" + id);
    }




}
