package com.library.Pages;

import com.library.Utilities.BrowserUtils;
import com.library.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Books extends BasePage {
    @FindBy(xpath = "//a[@href='tpl/add-book.html']")
    public WebElement addBookBtn;

    @FindBy (id = "book_categories")
    public WebElement bookCategoriesDropDown;

    @FindBy (name = "tbl_books_length")
    public WebElement viewPerPage;

    @FindBy (xpath = "//input[@type='search']")
    public WebElement searchBox;

    By booksInfo = By.xpath("//div[@id='tbl_books_info']");

    By paginationLoc = By.xpath("//ul[@class='pagination']");

    By firstPageLoc = By.xpath("//a[@title='First']");

    By prevPageLoc = By.xpath("//a[@title='Prev']");

    By lastPageLoc = By.xpath("//a[@title='Last']/..");

    By nextPageLoc = By.xpath("//a[@title='Next']");

    By activePageLoc = By.xpath("//li[contains(@class, 'active')]");

    public int currentPage = 1;
    public int pageCount = 0;
    public String currentSorting = "Descending";
    public int bookCount;

    public Books() {
        setInitialBookCount();
        setPageCount();

    }

    private void setInitialBookCount() {
        waitTable();
        String fullInfo = (Driver.getDriver().findElement(booksInfo)).getText();
        String[] words = fullInfo.split(" ");
        String bookCt = "";
        for(int i = 0; i < words.length; i++) {
            if(i+1 < words.length && words[i+1].equalsIgnoreCase("entries")) {
                bookCt += words[i];
            }
        }
        bookCt = bookCt.trim();
        bookCt = bookCt.replace(",","");
        this.bookCount = Integer.parseInt(bookCt);
    }

    private void setPageCount() {
        goToLastPage();
        int act = getActivePageNumber();
        pageCount = act;
        goToFirstPage();
    }

    public void goToLastPage(){
        waitProcessing();
        if(currentPage != pageCount) {
            WebElement lastPage = Driver.getDriver().findElement(lastPageLoc);
            BrowserUtils.bringIntoView(lastPage);
            String attribute = lastPage.getAttribute("class");
            if(!attribute.contains("disabled")) {
                lastPage.click();
            }
            setCurrentPage();
        }

    }

    public void goToFirstPage() {
        waitProcessing();
        if(currentPage != 1) {
            Driver.getDriver().findElement(firstPageLoc).click();
            setCurrentPage();
        }
    }

    public void goToNextPage() {
        waitProcessing();
        if(currentPage != pageCount) {
            Driver.getDriver().findElement(nextPageLoc).click();
            setCurrentPage();
        }
    }

    public void goToPrevPage() {
        waitProcessing();
        if(currentPage != 1) {
            Driver.getDriver().findElement(prevPageLoc).click();
            setCurrentPage();
        }
    }

    private int getActivePageNumber(){
        waitProcessing();
        WebElement activePage = Driver.getDriver().findElement(activePageLoc);
        String currPage = activePage.findElement(By.tagName("a")).getText();
        return Integer.parseInt(currPage.trim());
    }

    private void setCurrentPage() {
        currentPage = getActivePageNumber();
    }

    public List<String> getViewPerPageOptions() {
        Select select = new Select(viewPerPage);
        List<String> list = new ArrayList<>();
        for(WebElement option : select.getOptions()) {
            list.add(option.getText());
        }
        return list;
    }

    public void setViewPerPage(String count) {
        Select select = new Select(viewPerPage);
        select.selectByVisibleText(count);
        setPageCount();
    }

    public void search(String searchString) {
        searchBox.sendKeys(searchString);
        waitProcessing();
    }


    private void waitProcessing() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.id("tbl_books_processing"));
                String att = element.getAttribute("style");
                return att.contains("none");

            }
        });
    }

    private void waitTable() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody//tr//td"), 12));
    }

}
