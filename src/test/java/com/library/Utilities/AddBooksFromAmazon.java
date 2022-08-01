package com.library.Utilities;

import com.library.Pages.Books;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AddBooksFromAmazon {

    public static List<List<String>> books = new ArrayList<>();

    private static void openAmazonAndSwithToIt() {
        ((JavascriptExecutor)Driver.getDriver()).executeScript("window.open('https://www.amazon.com/','_blank');");
        Set<String> handles = Driver.getDriver().getWindowHandles();
        for(String str : handles) {
            Driver.getDriver().switchTo().window(str);
            if(Driver.getDriver().getCurrentUrl().contains("amazon")) {
                break;
            }
        }
    }

    private static void closeAmazon() {
        if(Driver.getDriver().getCurrentUrl().contains("amazon")) {
            Driver.getDriver().close();
        }
    }

    private static List<String> getBookCategories(Books books) {
        books.addBookBtn.click();
        List<String> categories = books.getBookCategoryOptions();
        books.newBookCloseBtn.click();
        return categories;
    }

    public static void getOneBookForEachCategory(Books books) {
        // bookname
        // isbn
        // year
        // author
        // category
        // description
        List<String> categories = getBookCategories(books);

        openAmazonAndSwithToIt();

        /** TODO: WE NEED TO WORK ON THIS PART */

        WebElement amazonSearchBox = Driver.getDriver().findElement(By.id("twotabsearchtextbox"));
        for(String str : categories) {
            amazonSearchBox = Driver.getDriver().findElement(By.id("twotabsearchtextbox"));
            amazonSearchBox.clear();
            String searchText = str + " books ";
            amazonSearchBox.sendKeys(searchText + Keys.ENTER);
            BrowserUtils.clickWithJS(Driver.getDriver().findElement(By.id("p_n_feature_browse-bin/2656022011")));
            new WebDriverWait(Driver.getDriver(), 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//img[contains(@class, 's-image')]"), 15));
            getInformation();

        }

        closeAmazon();
    }

    public static String[] getInformation() {
        String [] bookInfo = new String[5];
        Driver.getDriver().findElement(By.xpath("//img[contains(@class, 's-image')]")).click();
        WebElement span = Driver.getDriver().findElement(By.xpath("//span[text()='Paperback']/parent::li"));
        span.click();
        getBookName();
        getYear();


        return bookInfo;
    }

    public static String getBookName() {
        return Driver.getDriver().findElement(By.id("productTitle")).getText();
    }

    public static String getYear() {
        String fullYearInfo = Driver.getDriver().findElement(By.xpath("(//div[@id='detailBullets_feature_div']//li//span)[3]")).getText();
        int length = fullYearInfo.length();
        return fullYearInfo.substring(length-5, length-1);
    }

    public static String getIsbn() {
        return "";
    }


}
