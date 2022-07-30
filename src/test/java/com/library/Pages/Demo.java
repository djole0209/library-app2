package com.library.Pages;

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
       System.out.println("Current page count = " + books.pageCount);
       System.out.println("Current page we are on = " + books.currentPage);

       for(String ct : books.getViewPerPageOptions()) {
           books.setViewPerPage(ct);
           System.out.println(ct + " items pp || total page count " + books.pageCount);
       }

       Driver.getDriver().quit();





   }






}
