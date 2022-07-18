package com.library.Pages;

import com.library.Utilities.ConfigurationReader;
import com.library.Utilities.Driver;
import org.junit.Test;

public class Demo {


   @Test
   public void test1(){
       Driver.getDriver().get(ConfigurationReader.getProperty("web.app.url"));

       LoginPage loginPage = new LoginPage();
       loginPage.login(ConfigurationReader.getProperty("librarian.username"), ConfigurationReader.getProperty("librarian.pass"));

       DashBoard dashBoard = new DashBoard();
      // System.out.println(dashBoard.getBookCount());
      // dashBoard.waitUntilLoaded();
       System.out.println(dashBoard.getUserCount());
       dashBoard.users.click();

       //dashBoard.logOut();

      Users user = new Users();

       System.out.println(user.getUserGroupOptions());
       user.chooseUserGroup("Students");

      // Driver.getDriver().close();



   }






}
