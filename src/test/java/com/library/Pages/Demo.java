package com.library.Pages;

import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;

public class Demo {


   @Test
   public void test1(){
       Driver.getDriver().get(ConfigurationReader.getProperty("web.app.url"));

       LoginPage loginPage = new LoginPage();
       loginPage.login(ConfigurationReader.getProperty("userName"), ConfigurationReader.getProperty("passWord"));

       BasePage basePage = new BasePage();
       System.out.println(basePage.menuOptionsAsList());

       basePage.logOut();

       loginPage.login(ConfigurationReader.getProperty("librarian.username"), ConfigurationReader.getProperty("librarian.pass"));

       System.out.println(basePage.menuOptionsAsList());

       basePage.logOut();

   }






}
