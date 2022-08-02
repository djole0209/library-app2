package com.library.Pages;

import com.library.Utilities.AddBooksFromAmazon;
import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {


   @Test
   public void test1() throws Exception{
       Driver.getDriver().get(ConfigurationReader.getProperty("web.app.url"));

       LoginPage loginPage = new LoginPage();
       loginPage.login(ConfigurationReader.getProperty("librarian.username"), ConfigurationReader.getProperty("librarian.pass"));

       BasePage basePage = new BasePage();
       basePage.books.click();
       Books books = new Books();
       AddBooksFromAmazon.add_200_Books(books);

       Driver.getDriver().quit();
   }


   /*bookInfo[0] = getBookName();
        bookInfo[1] = getIsbn();
        bookInfo[2] = getYear();
        bookInfo[3] = getAuthor();
        bookInfo[4] = getCategory();
        bookInfo[5] = getDescription();
        *
    */








}
