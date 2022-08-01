package com.library.Utilities;

import com.library.Pages.Books;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AddBooksFromAmazon {

    public static List<List<String>> booksList = new ArrayList<>();

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
        List<String> categories = getBookCategories(books);
        openAmazonAndSwithToIt();

        WebElement amazonSearchBox = null;
        try {
            amazonSearchBox = Driver.getDriver().findElement(By.id("twotabsearchtextbox"));
        } catch (Exception e) {
            amazonSearchBox = Driver.getDriver().findElement(By.xpath("//input[@id='nav-bb-search']"));
        }

        for(String str : categories) {
            if(booksList.size() == 4) {
                break;
            }
            try {
                amazonSearchBox = Driver.getDriver().findElement(By.id("twotabsearchtextbox"));
            } catch (Exception e) {
                amazonSearchBox = Driver.getDriver().findElement(By.xpath("//input[@id='nav-bb-search']"));
            }
            amazonSearchBox.clear();
            String searchText = str + " books paperback";
            amazonSearchBox.sendKeys(searchText + Keys.ENTER);
            //BrowserUtils.clickWithJS(Driver.getDriver().findElement(By.id("p_n_feature_browse-bin/2656022011")));
            //new WebDriverWait(Driver.getDriver(), 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//img[contains(@class, 's-image')]"), 15));
            String[] info = getInformation();
            List<String> book = new ArrayList<>(Arrays.asList(info));
            book.add(4, str);
            booksList.add(book);
        }

        closeAmazon();
    }

    public static String[] getInformation() {
        String [] bookInfo = new String[5];
        Driver.getDriver().findElement(By.xpath("//img[contains(@class, 's-image')]")).click();

        // swatchElement selected resizedSwatchElement
        List<WebElement> bookTypeOptions = Driver.getDriver().findElements(By.xpath("//li[contains(@class,'resizedSwatchElement')]"));
        for(WebElement element : bookTypeOptions) {
            if(element.getAttribute("innerHTML").contains("Paperback")) {
                element.click();
                break;
            }
        }
        bookInfo[0] = getBookName();
        bookInfo[1] = getIsbn();
        bookInfo[2] = getYear();
        bookInfo[3] = getAuthor();
        bookInfo[4] = getDescription();
        return bookInfo;
    }

    public static String getBookName() {
        return Driver.getDriver().findElement(By.id("productTitle")).getText();
    }

    public static String getYear() {
        //String fullYearInfo = Driver.getDriver().findElement(By.xpath("(//div[@id='detailBullets_feature_div']//li//span)[3]")).getText();
        String fullYearInfo = "";

        WebElement divInfo = Driver.getDriver().findElement(By.id("detailBullets_feature_div"));
        List<WebElement> listItems = divInfo.findElements(By.tagName("li"));
        for(WebElement element : listItems) {
            if(element.getAttribute("innerHTML").contains("Publisher")) {
                fullYearInfo += element.getText();
                break;
            }
        }

        if(fullYearInfo.length() == 0) {
            fullYearInfo = Driver.getDriver().findElement(By.xpath("(//div[@id='detailBullets_feature_div']//li//span)[3]")).getText();
        }

        int length = fullYearInfo.length();
        return fullYearInfo.substring(length-5, length-1);
    }

    public static String getIsbn() {
        String isbn = "";

        WebElement divInfo = Driver.getDriver().findElement(By.id("detailBullets_feature_div"));
        List<WebElement> listItems = divInfo.findElements(By.tagName("li"));
        for(WebElement element : listItems) {
            if(element.getAttribute("innerHTML").contains("ISBN")) {
                isbn = element.getText().substring(10);
                break;
            }
        }

        return isbn;
    }

    public static String getAuthor() {
        //return Driver.getDriver().findElement(By.xpath("//a[@class='a-link-normal contributorNameID']")).getText();
        String author = "";
        try {
            author = Driver.getDriver().findElement(By.xpath("//span[contains(@class, 'author')]//a[contains(@class,'a-link-normal')]")).getText();
        } catch (Exception e) {
            author += "Unknown";
        }
        return author;
    }

    ////span[contains(@class, 'author')]//a[contains(@class,'a-link-normal')]

    public static String getDescription() {
        WebElement bookInfoDiv = Driver.getDriver().findElement(By.id("bookDescription_feature_div"));
        List<WebElement> elements = bookInfoDiv.findElements(By.xpath(".//span"));
        String description = "";

        if(elements.size() > 1) {
            description += elements.get(0).getAttribute("innerHTML") + " " + elements.get(1).getAttribute("innerHTML");
        } else if(elements.size() == 1) {
            description += elements.get(0).getAttribute("innerHTML");
        } else {
            description = "No description provided on Amazon.com";
        }

        description = description.replace("<br>"," ");
//        while(description.indexOf("<br>") != -1) {
//            int firstBr = description.indexOf("<br>");
//            int secondBr = description.indexOf("<br>", firstBr + 2);
//            description = description.substring(0, firstBr) + description.substring(secondBr + 4);
//        }
        return description;
    }


}
