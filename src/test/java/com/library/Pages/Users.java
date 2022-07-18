package com.library.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class Users extends BasePage{

    @FindBy (id = "user_groups")
    public WebElement userGroupsDropDown;

    @FindBy (id = "user_status")
    public WebElement userStatusDropDown;

    @FindBy (xpath = "//input[@type='search']")
    public WebElement searchBox;

    @FindBy (xpath = "//section[@id='users']//a[contains(text(),'Add User')]")
    public WebElement addUserButton;

    @FindBy (xpath = "//select[@name='tbl_users_length']")
    public WebElement viewPerPage;

    public List<String> getUserGroupOptions(){
        Select select = new Select(userGroupsDropDown);
        List<String> list = new ArrayList<>();

        for(WebElement each : select.getOptions()){
            list.add(each.getText());
        }
        return list;
    }



    public void chooseUserGroup(String group){
        Select select = new Select(userGroupsDropDown);
        select.selectByVisibleText(group);
    }


}
