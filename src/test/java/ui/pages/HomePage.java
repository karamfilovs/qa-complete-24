package ui.pages;

import com.microsoft.playwright.Page;

public class HomePage extends BasePage {
    private final String clientTabSelector = "//a[contains(text(), 'Клиенти')]";

    public HomePage(Page page) {
        super(page);
    }

    public void clickClientsTab(){
        System.out.println("Clicking Clients tab");
        click(clientTabSelector);
    }
}
