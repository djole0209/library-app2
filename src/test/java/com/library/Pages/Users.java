package com.library.Pages;

import com.library.Utilities.BrowserUtils;
import com.library.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Users extends BasePage{

    @FindBy (id = "user_groups")
    public WebElement userGroupsDropDown;

    @FindBy (id = "user_status")
    public WebElement userStatusDropDown;

    @FindBy (xpath = "//input[@type='search']")
    public WebElement searchBox;

    @FindBy (xpath = "//section[@id='users']//a[contains(text(),'Add User')]")
    public WebElement addUserButton;

    @FindBy (xpath = "//select[@name='tbl_users_length']")
    public WebElement viewPerPage;

//    @FindBy(xpath = "//ul[@class='pagination']")
//    public WebElement pagination;
    By pagination = By.xpath("//ul[@class='pagination']");

//    @FindBy(xpath = "//a[@title='First']")
//    public WebElement firstPage;
    By firstPage = By.xpath("//a[@title='First']");

//    @FindBy(xpath = "//a[@title='Prev']")
//    public WebElement prevPage;
    By prevPage = By.xpath("//a[@title='Prev']");

//    @FindBy(xpath = "//a[@title='Last']/..")
//    public WebElement lastPage;
    By lastPage = By.xpath("//a[@title='Last']/..");

//    @FindBy(xpath = "//a[@title='Next']")
//    public WebElement nextPage;
    By nextPage = By.xpath("//a[@title='Next']");

//    @FindBy(xpath = "//li[@class='page-item active']")
//    public WebElement activePage;
    By activePage = By.xpath("//li[@class='page-item active']");


    public int currentPage = 1;
    public int pageCount = 0;

    public Users(){
        waitTable();
    }

    private int getActivePageNumber(){
        waitPagination();
        WebElement activePage = Driver.getDriver().findElement(By.xpath("//li[@class='page-item active']//a"));
        String currPageNumText = activePage.getText();
        System.out.println("FROM GETACTIVE " + currPageNumText);
        return Integer.parseInt(currPageNumText);
    }

    public void setPageCount() {
        this.goToLastPage();
        loadPagination();
        pageCount = getActivePageNumber();
        goToFirstPage();
    }

//    private String getActivePageNumber() {
//        String str = "";
//        waitUntil();
//        List<WebElement> list = Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']")).findElements(By.tagName("li"));
//        WebElement element = null;
//        int k = 0;
//
//        for(int i = list.size()-1; k < 3; i--, k++) {
//            element = list.get(i);
//            System.out.println(element.getText());
//            list = Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']")).findElements(By.tagName("li"));
//        }
//        return element.getText();
//    }

    private void loadPagination(){
        waitPagination();
        pagination = Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']"));

    }

    public List<String> getUserGroupOptions(){
        Select select = new Select(userGroupsDropDown);
        List<String> list = new ArrayList<>();

        for(WebElement each : select.getOptions()){
            list.add(each.getText());
        }
        return list;
    }

    public void chooseUserGroup(String group){
        Select select = new Select(userGroupsDropDown);
        select.selectByVisibleText(group);
    }

    public void setCurrentPage() {
        currentPage = getActivePageNumber();
    }

    public int getCurrentPageNumber() {
        return currentPage;
    }

//    public void goToPrevPage() {
//        loadPagination();
//        if(currentPage != 1) {
//            BrowserUtils.bringIntoView(prevPage);
//            prevPage.click();
//        }
//    }

    public void goToFirstPage() {
        loadPagination();
        if(currentPage != 1) {
            //BrowserUtils.bringIntoView(firstPage);
            //firstPage.click();
            Driver.getDriver().findElement(firstPage).click();
        }
    }

    public void goToNextPage() {
        loadPagination();
        nextPage.click();
    }

    public void goToLastPage() {
        //BrowserUtils.bringIntoView(lastPage);

        //BrowserUtils.clickWithJS(lastPage);
        lastPage.click();
        //waitTable();
    }

    private void waitTable() {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody//tr//td"), 12));
    }


    private void waitPagination() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@class='pagination']//li"), 8));
    }







}
