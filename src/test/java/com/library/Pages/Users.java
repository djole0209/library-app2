package com.library.Pages;

import com.github.javafaker.Bool;
import com.github.javafaker.Faker;
import com.library.Utilities.BrowserUtils;
import com.library.Utilities.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @FindBy (id = "user_status")
    public WebElement userStatus;

    @FindBy (xpath = "//th[@data-name='id']")
    public WebElement userIdHeader;

    @FindBy (xpath = "//th[@data-name='full_name']")
    public WebElement fullNameHeader;

    @FindBy (xpath = "//th[@data-name='email']")
    public WebElement emailHeader;

    @FindBy (xpath = "//th[@data-name='group_name']")
    public WebElement groupHeader;

    @FindBy (xpath = "//th[@data-name='status']")
    public WebElement statusHeader;

    @FindBy (xpath = "//input[@name='full_name']")
    public WebElement newUserFullName;

    @FindBy (xpath = "//input[@name='password']")
    public WebElement newUserPassword;

    @FindBy (xpath = "//input[@name='email']")
    public WebElement newUserEmail;

    @FindBy (xpath = "//select[@id='user_group_id']")
    public WebElement newUserGroupDropDown;
    // select

    @FindBy (xpath = "//select[@id='status']")
    public WebElement newUserStatusDropDown;

    @FindBy (xpath = "//input[@name='start_date']")
    public WebElement newUserStartDate;

    @FindBy (xpath = " //button[@type='cancel']")
    public WebElement newUserCancelButton;

    @FindBy (xpath = " //button[@type='submit']")
    public WebElement newUserSubmitButton;

    @FindBy (xpath = "//input[@name='end_date']")
    public WebElement newUserEndDate;

    @FindBy (id = "address")
    public WebElement newUserAddress;

    @FindBy (xpath = "//button[@aria-label='Close']")
    public WebElement newUserClosePage;

    By paginationLoc = By.xpath("//ul[@class='pagination']");

    By firstPageLoc = By.xpath("//a[@title='First']");

    By prevPageLoc = By.xpath("//a[@title='Prev']");

    By lastPageLoc = By.xpath("//a[@title='Last']/..");

    By nextPageLoc = By.xpath("//a[@title='Next']");

    By activePageLoc = By.xpath("//li[contains(@class, 'active')]");

    By usersInfo = By.id("tbl_users_info");


    public int currentPage = 1;
    public int pageCount = 0;
    public String currentSorting = "Descending";
    public int userCount;

    /**
     * TODO: I think we still need a constructor to set initial page count,
     * and total entries
     */
    public Users() {
        setInitialUserCount();
        setPageCount();
    }

    // to set the Initial number of users when the page is loaded
    private void setInitialUserCount() {
        waitTable();
        String fullInfo = Driver.getDriver().findElement(usersInfo).getText();
        String[] words = fullInfo.split(" ");
        String userCt = "";
        for(int i = 0; i < words.length; i++) {
            if(i+1 < words.length && words[i+1].equalsIgnoreCase("entries")) {
                userCt += words[i];
            }
        }
        userCt = userCt.trim();
        userCt = userCt.replace(",", "");
        this.userCount = Integer.parseInt(userCt);
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

    public void showActiveUsers() {
        new Select(userStatus).selectByValue("ACTIVE");
        waitProcessing();
    }

    public void showInactiveUsers() {
        new Select(userStatus).selectByValue("INACTIVE");
        waitProcessing();
    }

    public void search(String searchString) {
        searchBox.sendKeys(searchString);
        waitProcessing();
    }

    public void sortByUserId(String sortType) {
        if(!currentSorting.equalsIgnoreCase(sortType)) {
            userIdHeader.click();
            if(currentSorting.equalsIgnoreCase("Descending")) {
                currentSorting = "Ascending";
            } else {
                currentSorting = "Descending";
            }
        }
        waitProcessing();
    }

    public void addNewUser(String fullName, String password, String email, String userGroup,String userStatus, String endDate, String userAddress){
        userGroup = userGroup.substring(0,1).toUpperCase() + userGroup.substring(1).toLowerCase();
        userStatus = userStatus.toUpperCase();

        Faker faker = new Faker();
        newUserFullName.sendKeys(faker.funnyName().toString());
        newUserPassword.sendKeys(faker.bothify("??#?##?"));
        newUserEmail.sendKeys(faker.internet().emailAddress());
        new Select(newUserGroupDropDown).selectByVisibleText(userGroup);
        new Select(newUserStatusDropDown).selectByVisibleText(userStatus);
        /** TODO: decide on end date logic */
        newUserEndDate.sendKeys(endDate);
        newUserAddress.sendKeys(userAddress);
        newUserSubmitButton.click();
    }

    public String getStartDateAsStr() {
        return this.newUserStartDate.getAttribute("value");
    }

    public String getStartDateYear() {
        return getStartDateAsStr().substring(0,4);
    }

    public String getStartDateMonth() {
        return getStartDateAsStr().substring(5,7);
    }

    public String getStartDateDay() {
        return getStartDateAsStr().substring(8);
    }


    // this is the complicated version, but still works
    // good logic, we did not want to lose this
    public String getStartDateAsString() {
        newUserStartDate.click();
        String copy = Keys.chord(Keys.CONTROL, "A", "C");
        newUserStartDate.sendKeys(copy);
        String paste = Keys.chord(Keys.CONTROL, "V");

        ((JavascriptExecutor)Driver.getDriver()).executeScript("window.open('https://www.google.com','_blank');");

        Set<String> windowHandles = Driver.getDriver().getWindowHandles();

        for(String handle : windowHandles) {
            Driver.getDriver().switchTo().window(handle);
            if(Driver.getDriver().getCurrentUrl().contains("google")) {
                break;
            }
        }
        Driver.getDriver().findElement(By.name("q")).sendKeys(paste + Keys.ENTER);
        String dateAsText = Driver.getDriver().findElement(By.name("q")).getAttribute("value");
        Driver.getDriver().close();

        return dateAsText;
    }



    /** TODO: Edit user info */



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
