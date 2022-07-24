package com.library.Pages;

import com.github.javafaker.Bool;
import com.library.Utilities.BrowserUtils;
import com.library.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
    By paginationLoc = By.xpath("//ul[@class='pagination']");

//    @FindBy(xpath = "//a[@title='First']")
//    public WebElement firstPage;
    By firstPageLoc = By.xpath("//a[@title='First']");

//    @FindBy(xpath = "//a[@title='Prev']")
//    public WebElement prevPage;
    By prevPageLoc = By.xpath("//a[@title='Prev']");

//    @FindBy(xpath = "//a[@title='Last']/..")
//    public WebElement lastPage;
    By lastPageLoc = By.xpath("//a[@title='Last']/..");

//    @FindBy(xpath = "//a[@title='Next']")
//    public WebElement nextPage;
    By nextPageLoc = By.xpath("//a[@title='Next']");

//    @FindBy(xpath = "//li[@class='page-item active']")
//    public WebElement activePage;
    By activePageLoc = By.xpath("//li[contains(@class, 'active')]");


    public int currentPage = 1;
    public int pageCount = 0;

    public Users() {
        waitProcessing();
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setPageCount() {
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


    private void waitProcessing() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.id("tbl_users_processing"));
                String att = element.getAttribute("style");
                return att.contains("none");

            }
        });
    }

    private void waitDivs(){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//section[@id='users']//div"), 35));
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
