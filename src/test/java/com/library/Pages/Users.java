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

    @FindBy(xpath = "//ul[@class='pagination']")
    public WebElement pagination;

    @FindBy(xpath = "//a[@title='First']")
    public WebElement firstPage;

    @FindBy(xpath = "//a[@title='Prev']")
    public WebElement prevPage;

    @FindBy(xpath = "//a[@title='Last']")
    public WebElement lastPage;

    @FindBy(xpath = "//a[@title='Next']")
    public WebElement nextPage;

    @FindBy(xpath = "//li[@class='page-item active']")
    public WebElement activePage;


    public int currentPage = 1;
    public int pageCount = 0;

    public Users() {
        waitUntil();
        goToLastPage();
        String currentPageCount = getActivePageNumber();
        System.out.println("From constructor " + currentPageCount);
        pageCount = Integer.parseInt(currentPageCount);
        goToFirstPage();
    }

    private String getActivePageNumber() {
        String str = "";
        waitUntil();
        List<WebElement> list = Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']")).findElements(By.tagName("li"));
        WebElement element = null;
        int k = 0;

        for(int i = list.size()-1; k < 3; i--, k++) {
            element = list.get(i);
            System.out.println(element.getText());
            list = Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']")).findElements(By.tagName("li"));
        }
        return element.getText();
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
        currentPage = Integer.parseInt(activePage.getText());
    }

    public int getCurrentPageNumber() {
        return currentPage;
    }

    public void goToPrevPage() {
        if(currentPage != 1) {
            BrowserUtils.bringIntoView(prevPage);
            prevPage.click();
        }
    }

    public void goToFirstPage() {
        if(currentPage != 1) {
            BrowserUtils.bringIntoView(firstPage);
            firstPage.click();

        }
    }

    public void goToNextPage() {
        //BrowserUtils.bringIntoView(nextPage);
        nextPage.click();
    }

    public void goToLastPage() {
        BrowserUtils.bringIntoView(lastPage);
        lastPage.click();
    }

    private void waitUntil() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@class='pagination']//li"), 5));
    }







}
