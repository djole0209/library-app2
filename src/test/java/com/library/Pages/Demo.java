package com.library.Pages;

import com.library.Utilities.AddBooksFromAmazon;
import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.Set;

public class Demo {


   @Test
   public void test1() throws Exception{
       Driver.getDriver().get(ConfigurationReader.getProperty("web.app.url"));

       LoginPage loginPage = new LoginPage();
       loginPage.login(ConfigurationReader.getProperty("librarian.username"), ConfigurationReader.getProperty("librarian.pass"));

       BasePage basePage = new BasePage();
       basePage.books.click();
       Books books = new Books();

       System.out.println(books.currentPage);
       System.out.println(books.pageCount);

       AddBooksFromAmazon.getOneBookForEachCategory(books);






       Driver.getDriver().quit();





   }






}
