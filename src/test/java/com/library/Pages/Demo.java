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


       for(int i = 1; i < usersPage.pageCount; i++) {
           System.out.println("\nCurrent Page => " + usersPage.getCurrentPage());
           usersPage.goToNextPage();
       }

       System.out.println("\nCurrent Page => " + usersPage.getCurrentPage());

       List<String> options = usersPage.getViewPerPageOptions();
       for(int i = 0; i < options.size(); i++) {
           usersPage.setViewPerPage(options.get(i));
           System.out.println("Show records => " + options.get(i) +  " Page count for this view => " + usersPage.pageCount + " Current Page => " + usersPage.currentPage);
       }

       usersPage.setViewPerPage("10");

       Driver.getDriver().quit();





   }






}
