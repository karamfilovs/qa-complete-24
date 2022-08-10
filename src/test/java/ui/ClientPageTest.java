package ui;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.dto.Client;
import ui.pages.ClientPage;
import ui.pages.HomePage;
import ui.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientPageTest extends BaseUITest {


    @Test
    @DisplayName("Can add client")
    void canAddClient() {
        clientAPI.deleteAll();
        LoginPage loginPage = new LoginPage(page);
        ClientPage clientPage = new ClientPage(page);
        HomePage homePage = new HomePage(page);
        loginPage.defaultLogin();
        homePage.clickClientsTab();
        clientPage.clickNewClientButton();
        clientPage.enterFirmName("Pragmatic LLC");
        clientPage.enterFirmTown("Sofia");
        clientPage.enterFirmAddress("Mayor Gortalov 13");
        clientPage.clickSaveButton();
        //Assert success message
        assertTrue(clientPage.getSuccessMessage().contains("Клиентът е добавен успешно."));
    }

    @Test
    @DisplayName("Correct message is dispalyed when there are no clients")
    void correctMessageIsDisplayedWhenThereAreNoClients() {
        clientAPI.deleteAll();
        LoginPage loginPage = new LoginPage(page);
        ClientPage clientPage = new ClientPage(page);
        HomePage homePage = new HomePage(page);
        loginPage.defaultLogin();
        homePage.clickClientsTab();
        Assertions.assertEquals("Все още нямате добавени клиенти.", clientPage.getEmptyListMessage());
    }

    @Test
    @DisplayName("Can search for existing clients")
    void canSearchForExistingClients(){
        int vat = RandomUtils.nextInt(100000, 10000000);
        clientAPI.deleteAll();
        String clientName = "TestSearch" + vat;
        Client client = new Client(null, clientName, "Sofia", "Sofia", false, "Ivan", vat + "");
        clientAPI.createClient(client);
        LoginPage loginPage = new LoginPage(page);
        ClientPage clientPage = new ClientPage(page);
        HomePage homePage = new HomePage(page);
        loginPage.defaultLogin();
        homePage.clickClientsTab();
        clientPage.searchForClientByName(clientName);
        assertTrue(clientPage.getTableInfo().contains(clientName));
    }
}
