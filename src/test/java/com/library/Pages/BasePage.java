package com.library.Pages;

import com.library.Utilities.BrowserUtils;
import com.library.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

// about:blank
public class BasePage {
    // No page elements added
    @FindBy(xpath = "//a[@href='#books']")
    public WebElement books;

    @FindBy(xpath = "//a[@href='#borrowing-books']")
    public WebElement borrowingBooks;

    @FindBy(xpath = "//a[@href='#dashboard']")
    public WebElement dashboard;

    @FindBy (xpath = "//span[.='Users']")
    public WebElement users;

    @FindBy (id = "user_avatar")
    public WebElement userImage;

    @FindBy (xpath = "//a[.='Log Out']")
    public WebElement LogOutBtn;

    @FindBy (xpath = "//ul[@id='menu_item']//li")
    public List<WebElement> menuItems;

    boolean isProfileOpen = false;


    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    private void openProfileDropdown(){
        if(!isProfileOpen){
            userImage.click();
            isProfileOpen = true;
        }
    }

    public void logOut(){
        openProfileDropdown();
//        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
//        js.executeScript("arguments[0].scrollIntoView(true);", LogOutBtn);
        LogOutBtn.click();
        isProfileOpen = false;
    }

    public List<String> menuOptionsAsList(){
        List<String> result = new ArrayList<>();

        for(WebElement each : menuItems){
            WebElement item = each.findElement(By.tagName("span"));
            result.add(item.getText());
        }
        return result;
    }



}