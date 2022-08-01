package com.library.Pages;

import com.library.Utilities.AddBooksFromAmazon;
import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

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
       AddBooksFromAmazon.getOneBookForEachCategory(books);

       List<List<String>> booksInfoFromAmazon = AddBooksFromAmazon.booksList;
       for(List<String> eachBook : booksInfoFromAmazon) {
           System.out.println(eachBook);
       }



//       System.out.println(books.currentPage);
//       System.out.println(books.pageCount);
//       String windowHandle = Driver.getDriver().getWindowHandle();
//
//       AddBooksFromAmazon.getOneBookForEachCategory(books);
//
//       List<List<String>> booksInfoFromAmazon = AddBooksFromAmazon.booksList;
//       Driver.getDriver().switchTo().window(windowHandle);
//       int i = 0;
//       for(List<String> eachBook : booksInfoFromAmazon) {
//           books.addBook(eachBook, i);
//           i++;
//       }

       //books.addBook("Selam", "Yo", "2012");

       Driver.getDriver().quit();
   }










}
