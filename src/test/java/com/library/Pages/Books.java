package com.library.Pages;

import com.library.Utilities.BrowserUtils;
import com.library.Utilities.Driver;
import org.openqa.selenium.*;
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

    @FindBy (xpath = "//input[@name='name']")
    public WebElement newBookName;

    @FindBy (xpath = "//input[@name='isbn']")
    public WebElement newBookISBN;

    @FindBy (xpath = "//input[@name='year']")
    public WebElement newBookYear;

    @FindBy (xpath = "//input[@name='author']")
    public WebElement newBookAuthor;

    @FindBy (xpath = "//select[@name='book_category_id']")
    public WebElement newBookCategory;

    @FindBy (id = "description")
    public WebElement newBookDescription;

    @FindBy (xpath = "//button[@type='submit']")
    public WebElement newBookSaveChangesBtn;

    @FindBy (xpath = "//button[@type='cancel']")
    public WebElement newBookCloseBtn;



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

    public void addBook(List<String> bookInfo, int index) {
        this.addBookBtn.click();
        newBookName.sendKeys("G4" + bookInfo.get(0));
        newBookISBN.sendKeys(bookInfo.get(1));
        newBookYear.sendKeys(bookInfo.get(2));
        newBookAuthor.sendKeys(bookInfo.get(3));
//        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute('value', arguments[1]); ", newBookAuthor, bookInfo.get(3));
        new Select(newBookCategory).selectByIndex(index);
        newBookDescription.sendKeys((bookInfo.get(5).length() > 50 ? bookInfo.get(5).substring(0,50) : bookInfo.get(5)));
        newBookSaveChangesBtn.click();
    }

    public void addBook(String bookName, String author, String year) {
        this.addBookBtn.click();
        newBookName.sendKeys(bookName);
        newBookAuthor.sendKeys(author);
        newBookYear.sendKeys(year);
        newBookSaveChangesBtn.click();
    }

    public void addBook(String bookName, String author, String year, String description) {
        this.addBookBtn.click();
        newBookName.sendKeys(bookName);
        newBookAuthor.sendKeys(author);
        newBookYear.sendKeys(year);
        newBookDescription.sendKeys(description);
        newBookSaveChangesBtn.click();
    }

    public void addBook(String bookName, String isbn, String year, String author, String bookcategory, String description) {
        List<String> bookCategoryOptions = getBookCategoryOptions();
        int i = 0;
        for(; i < bookCategoryOptions.size(); i++) {
            if(bookCategoryOptions.get(i).toLowerCase().contains(bookcategory.toLowerCase())) {
                break;
            }
        }
        this.addBookBtn.click();
        newBookName.sendKeys(bookName);
        newBookAuthor.sendKeys(author);
        newBookDescription.sendKeys(description);
        newBookSaveChangesBtn.click();
        new Select(newBookCategory).selectByIndex(i);
        newBookDescription.sendKeys(description);
        newBookSaveChangesBtn.click();
    }

    public List<String> getBookCategoryOptions(){
        Select select = new Select(newBookCategory);
        List<String> list = new ArrayList<>();

        for(WebElement each : select.getOptions()){
            list.add(each.getText());
        }
        return list;
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
        wait.ignoring(TimeoutException.class);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody//tr//td"), 12));
    }

}

/**
 * TODO: add book if the book does not exist
 * TODO: we can create a new table, or query from the current table
 * TODO: if we alter the table, we should commit, so that when we query again it will show us if we have it or not
 */