package com.library.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Books extends BasePage {
    @FindBy(xpath = "//a[@href='tpl/add-book.html']")
    public WebElement addBookBtn;

    @FindBy (id = "book_categories")
    public WebElement bookCategoriesDropDown;

    @FindBy (name = "tbl_books_length")
    public WebElement viewPerPage;

    @FindBy (xpath = "//input[@type='search']")
    public WebElement searchBox;




}
