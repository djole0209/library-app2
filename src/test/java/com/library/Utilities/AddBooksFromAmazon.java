package com.library.Utilities;

import com.library.Pages.Books;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class AddBooksFromAmazon {

    public static List<List<String>> booksList = new ArrayList<>();
    public static boolean clickedFromFirstSearch = false;

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
            clickedFromFirstSearch = false;
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
            for(int i = 0; i < 25; i++) {
                String[] info = getInformation();
                List<String> bookInd = new ArrayList<>(Arrays.asList(info));
                booksList.add(bookInd);
            }
        }

        closeAmazon();
    }

    public static String[] getInformation() {
        String [] bookInfo = new String[6];
        if(clickedFromFirstSearch == false) {
            Driver.getDriver().findElement(By.xpath("//img[contains(@class, 's-image')]")).click();
            clickedFromFirstSearch = true;
        } else {
            openNewBook();
        }



        // swatchElement selected resizedSwatchElement
//        List<WebElement> bookTypeOptions = Driver.getDriver().findElements(By.xpath("//li[contains(@class,'resizedSwatchElement')]"));
//        for(WebElement element : bookTypeOptions) {
//            if(element.getAttribute("innerHTML").contains("Paperback")) {
//                element.click();
//                break;
//            }
//        }

        try {
            //Point newPoint = new Point(540,720);
//            new Actions(Driver.getDriver()).moveToElement(Driver.getDriver().findElement(By.xpath("//i[@class='a-icon a-icon-close']/.."))).click().build().perform();
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'a-popover')]")));
            //Driver.getDriver().findElement(By.xpath("//div[contains(@id, 'a-popover')]"));
            String style = popup.getAttribute("style");
            if(style.contains("visible")) {
                System.out.println("WE FOUND THE DIV");
                //Driver.getDriver().findElement(By.id("productTitle")).click();
//                Driver.getDriver().findElement(By.xpath("//textarea[@id='af-free-text']")).sendKeys(Keys.ESCAPE)
//                ((JavascriptExecutor)Driver.getDriver()).executeScript("arguments[0].setAttribute('aria-hidden','true');", popup);
              WebElement close = popup.findElement(By.tagName("button"));
              if(close != null) {
                  System.out.println("FOUND THE CLOSE");
              }
                ((JavascriptExecutor)Driver.getDriver()).executeScript("arguments[0].click();", close);
                //Driver.getDriver().navigate().refresh();

            }
        } catch (Exception e) {}

        bookInfo[0] = getBookName();
        bookInfo[1] = getIsbn();
        bookInfo[2] = getYear();
        bookInfo[3] = getAuthor();
        bookInfo[4] = getCategory();
        bookInfo[5] = getDescription();
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
            author = Driver.getDriver().findElement(By.xpath("//span[contains(@class, 'author')]//a[contains(@class,'a-link-normal')]")).getAttribute("innerHTML");
        } catch (Exception e) {
            author += "Unknown";
        }
        author = author.replace("Visit Amazon's","");
        author = author.replace("Page", "");
        author = author.trim();
        return author;
    }

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


    public static String getCategory() {
        String category = "";
        String str = "";
        try {
            str = Driver.getDriver().findElement(By.xpath("//ul[contains(@class, 'zg_hrsr')]")).getAttribute("innerHTML");
        } catch(Exception e) {
            str = "None";
        }

        if (str.contains("Business") || str.contains("Money") || str.contains("Action") || str.contains("Adventure")) {
            category = "Action and Adventure";
        } else if (str.contains("Art") || str.contains("Photography") || str.contains("Music") || str.contains("Calendars")) {
            category = "Anthology";
        } else if (str.contains("Literature") || str.contains("Novel") || str.contains("Fiction") || str.contains("Classic")) {
            category = "Classic";
        } else if (str.contains("LGBT") || str.contains("Mystery") || str.contains("Thriller") || str.contains("Suspense") || str.contains("Crime") || str.contains("Detective")) {
            category = "Crime and Detective";
        } else if (str.contains("Drama") || str.contains("Hobbies") || str.contains("Hobby") || str.contains("Home") || str.contains("Garden")) {
            category = "Drama";
        } else if (str.contains("Fable") || str.contains("Health") || str.contains("Fitness") || str.contains("Diet") || str.contains("Story")) {
            category = "Fable";
        } else if (str.contains("Tale") || str.contains("Fairy") || str.contains("Children") || str.contains("Kids")) {
            category = "Fairy Tale";
        } else if (str.contains("Fan") || str.contains("Fiction") || str.contains("Literature")) {
            category = "Fan-Fiction";
        } else if (str.contains("Politics") || str.contains("Social") || str.contains("Sciences") || str.contains("Travel")) {
            category = "Poetry";
        } else if (str.contains("Biography") || str.contains("Reference") || str.contains("Test") || str.contains("Preparation")) {
            category = "Memoir";
        } else if (str.contains("Engineering") || str.contains("Transportation")) {
            category = "Essay";
        } else if (str.contains("Education") || str.contains("Teaching") || str.contains("Teen") || str.contains("Young") || str.contains("Adult")) {
            category = "Short Story";
        } else if (str.contains("Medical") || str.contains("Romance")) {
            category = "Romance";
        } else if (str.contains("Humor") || str.contains("Entertainment") || str.contains("Science") || str.contains("Math")) {
            category = "Humor";
        } else if (str.contains("Art") || str.contains("Photography") || str.contains("Music") || str.contains("Calendars")) {
            category = "Anthology";
        } else if (str.contains("Fantasy") || str.contains("Cookbooks") || str.contains("Food") || str.contains("Wine")) {
            category = "Fantasy";
        } else if (str.contains("Historical fiction") || str.contains("Christian Books & Bibles") || str.contains("History") || str.contains("Religion & Spirituality")) {
            category = "Historical fiction";
        } else if (str.contains("Horror") || str.contains("Law") || str.contains("Parenting & Relationship")) {
            category = "Horror";
        } else if (str.contains("Science Fiction") || str.contains("Computer & Techonology") || str.contains("Science Fiction & Fantasy") || str.contains("Wine")) {
            category = "Science Fiction";
        } else if (str.contains("Biography/Autobiography") || str.contains("Self-Help") || str.contains("Sports & Outdoors")) {
            category = "Biography/Autobiography";
        } else {
            category = "Humor";
        }
        return category;
    }


    private static void openNewBook() {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
            WebElement elem = null;
            try {
                elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sp_detail']//ol")));
            } catch (Exception e) {
                try {
                    elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sp_detail_thematic-same_genre_books']//ol")));
                } catch (Exception er) {
                    elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='similarities_feature_div']//ol")));
                }
            }
            //WebElement elem = Driver.getDriver().findElement(By.xpath("//div[@id='sp_detail']//ol"));
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elem);
            //List<WebElement> relatedBooks = Driver.getDriver().findElements(By.xpath("(//ol[@role='list'])[4]//li"));
            List<WebElement> relatedBooks = elem.findElements(By.xpath(".//li"));
            WebElement book = relatedBooks.get(ThreadLocalRandom.current().nextInt(0, relatedBooks.size()));
            //BrowserUtils.highlight(book);
            try {
                book.click();
            } catch (Exception e) {
                book = relatedBooks.get(ThreadLocalRandom.current().nextInt(0, relatedBooks.size()));
                book.click();
            }
            //WebElement bookLink = book.findElement(By.xpath(".//a"));
            //((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", book);
    }

}

/*
 try {
            amazonSearchBox = Driver.getDriver().findElement(By.id("twotabsearchtextbox"));
        } catch (Exception e) {
            amazonSearchBox = Driver.getDriver().findElement(By.xpath("//input[@id='nav-bb-search']"));
        }
        amazonSearchBox.sendKeys("action and adventure books paperback" + Keys.ENTER);
        try {
            Driver.getDriver().findElement(By.xpath("//li[contains(@class,'resizedSwatchElement')]")).click();
        } catch (Exception e) {
            Driver.getDriver().findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']")).click();
        }


 */