package com.library.Pages;

import com.library.Utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashBoardPage extends BasePage{


    @FindBy (id = "user_count")
    public WebElement usersCount;

    @FindBy (id = "book_count")
    public WebElement bookCount;

    @FindBy (id = "borrowed_books")
    public WebElement borrowedBooks;

    public DashBoardPage(){
        waitUntilLoaded();
    }
    public int getUserCount(){
        String result = usersCount.getText();
        return Integer.parseInt(result);
    }

    public int getBookCount(){
        String result = bookCount.getText();
        return Integer.parseInt(result);
    }

    public int getBorrowedBooksCount(){
        String result = borrowedBooks.getText();
        return Integer.valueOf(result);
    }

    public void waitUntilLoaded(){
       new WebDriverWait(Driver.getDriver(), 5).until(new ExpectedCondition<Boolean>() {
           @Override
           public Boolean apply(WebDriver driver) {
               return getBookCount() > 5;
           }
       });
    }


}
