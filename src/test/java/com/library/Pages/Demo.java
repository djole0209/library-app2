package com.library.Pages;

import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;

import java.util.List;

public class Demo {


   @Test
   public void test1() throws Exception{
       Driver.getDriver().get(ConfigurationReader.getProperty("web.app.url"));

       LoginPage loginPage = new LoginPage();
       loginPage.login(ConfigurationReader.getProperty("librarian.username"), ConfigurationReader.getProperty("librarian.pass"));

       BasePage basePage = new BasePage();
       basePage.users.click();

       Users usersPage = new Users();

       System.out.println(usersPage.getCurrentPage());
       usersPage.setPageCount();
       System.out.println("Page count " + usersPage.pageCount);

       usersPage.sortByUserId("Descending");
       System.out.println("Current sorting type = " + usersPage.currentSorting);

       usersPage.sortByUserId("Ascending");
       System.out.println("Current sorting type = " + usersPage.currentSorting);

       usersPage.sortByUserId("Descending");
       System.out.println("Current sorting type = " + usersPage.currentSorting);


       //Driver.getDriver().quit();





   }






}
