package ui.pages;

import com.microsoft.playwright.Page;

public class ClientPage extends BasePage {
    private final String newButtonSelector = "//a[@class='newbtn selenium-add-client-button']";
    private final String firmNameSelector = "input[name='firm_name']";
    private final String firmAddressSelector = "textarea[name='firm_addr']";
    private final String firmTownSelector = "input[name='firm_town']";
    private final String submitButtonSelector = "input[name='do_submit']";
    private final String successMessageSelector = "#okmsg";
    private final String emptyListSelector = "#emptylist"; //ID selector start with #
    private final String expandSearchSelector = "#searchbtn";
    private final String companyNameSelector = "input[name='fnm']";
    private final String triggerSearchSelector = "input[name='s']";
    private final String tableSelector = "#fakturi_table";


    public void clickNewClientButton(){
        System.out.println("Clicking new client button");
        click(newButtonSelector);
    }

    public void enterFirmName(String name){
        System.out.println("Entering firm name: " + name);
        type(firmNameSelector, name);
    }

    public void enterFirmAddress(String address){
        System.out.println("Entering firm address: " + address);
        type(firmAddressSelector, address);
    }

    public void clickSaveButton(){
        System.out.println("Clicking Save client button");
        click(submitButtonSelector);
    }
    public void enterFirmTown(String town){
        System.out.println("Entering firm town: " + town);
        type(firmTownSelector, town);
    }

    public String getSuccessMessage(){
        return getText(successMessageSelector);
    }

    public String getEmptyListMessage(){
        return getText(emptyListSelector);
    }

    public String getTableInfo(){
        return getText(tableSelector);
    }

    public ClientPage(Page page) {
        super(page);
    }

    public void searchForClientByName(String name) {
        System.out.println("Searching for company by name:" + name);
        click(expandSearchSelector);
        type(companyNameSelector, name);
        click(triggerSearchSelector);
    }
}
