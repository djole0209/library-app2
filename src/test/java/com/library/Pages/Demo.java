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

       usersPage.setPageCount();

       System.out.println(usersPage.pageCount);

//       List<String> list = usersPage.getUserGroupOptions();
//       System.out.println(list);
//
//       for(int i = 0; i < list.size(); i++) {
//           usersPage.chooseUserGroup(list.get(i));
//           Thread.sleep(3000);
//       }


       //Driver.getDriver().quit();



   }






}
