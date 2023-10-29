package com.example.cucumber.common;

import com.example.cucumber.page.BasePage;
import com.example.cucumber.page.LoginPage;
import com.example.cucumber.page.NavigationBar;
import org.openqa.selenium.WebDriver;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class PageProvider extends BasePage {
    public PageProvider(WebDriver driver) {
        super(driver);
    }

    public NavigationBar getNavigationBar() {
        return new NavigationBar(driver);
    }

    public LoginPage getLoginPage() {
        return new LoginPage(driver);
    }
}
