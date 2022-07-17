package com.library.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// about:blank
public class BasePage {
    // No page elements added
    @FindBy(xpath = "//a[@href='#books']")
    public WebElement books;

    @FindBy(xpath = "//a[@href='#borrowing-books']")
    public WebElement borrowingBooks;

    @FindBy(xpath = "//a[@href='#dashboard']")
    public WebElement dashboard;



    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}