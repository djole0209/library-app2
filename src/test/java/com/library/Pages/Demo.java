package com.library.Pages;

import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;

public class Demo {


   @Test
   public void test1() throws Exception{
       Driver.getDriver().get(ConfigurationReader.getProperty("web.app.url"));

       LoginPage loginPage = new LoginPage();
       loginPage.login(ConfigurationReader.getProperty("librarian.username"), ConfigurationReader.getProperty("librarian.pass"));

       BasePage basePage = new BasePage();
       basePage.users.click();

       Users usersPage = new Users();

       System.out.println(usersPage.getCurrentPageNumber());
       System.out.println(usersPage.pageCount);

       for(int i = 0; i < usersPage.pageCount; i++) {
           usersPage.goToNextPage();
           Thread.sleep(1000);
       }

       Driver.getDriver().quit();



   }






}
