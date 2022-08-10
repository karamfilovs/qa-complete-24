package ui.pages;

import com.microsoft.playwright.Page;

public class BasePage {
    private static final String BASE_URL = "https://st2016.inv.bg";
    private Page page;

    public BasePage(Page page){
        this.page = page;
    }

    protected void type(String selector, String text){
        System.out.println("Typing text:" + text);
        page.fill(selector, text);
    }

    protected void select(String selector, String value){
        System.out.println("Selecting value:" + value);
        page.selectOption(selector, value);
    }

    protected void navigate(String path){
        System.out.println("Navigation to page:" + path);
        page.navigate(BASE_URL + path);
    }

    protected void click(String selector){
        System.out.println("Clicking button");
        page.click(selector);
    }

    protected String getText(String selector){
        String text = page.textContent(selector).trim();
        System.out.println("Text found:" + text);
        return text;
    }
}
