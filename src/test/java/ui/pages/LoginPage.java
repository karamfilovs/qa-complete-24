package ui.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private static final String emailSelector = "#loginusername";
    private static final String passwordSelector = "#loginpassword";
    private static final String loginButtonSelector = "#loginsubmit";
    private static final String userPanelSelector = "//div[@class='userpanel-header']";

    public LoginPage(Page page){
        super(page);
    }

    public void enterEmail(String email){
        type(emailSelector, email);
    }

    public void enterPassword(String password){
        type(passwordSelector, password);
    }

    public void clickLoginButton(){
        click(loginButtonSelector);
    }

    public void login(String email, String password){
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void defaultLogin(){
        login("karamfilovs@gmail.com", "123456");
    }

    public String getLoggerUser(){
        return getText(userPanelSelector);
    }
}
