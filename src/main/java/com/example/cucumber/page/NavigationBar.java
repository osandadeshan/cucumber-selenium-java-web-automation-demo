package com.example.cucumber.page;

import com.example.cucumber.common.constant.NavigationBarOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class NavigationBar extends BasePage {
    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public void checkNavigationBarVisibility() {
        waitUntilElementVisible(By.id("navbarExample"));
    }

    public WebElement getNavigationOptionElement(NavigationBarOption navigationBarOption) {
        return getElement(
                By.xpath(
                        "//a[@class='nav-link'][contains(.,'" + navigationBarOption.getName() + "')]"
                )
        );
    }

    public void clickOnNavOption(NavigationBarOption navigationBarOption) {
        getNavigationOptionElement(navigationBarOption).click();
    }

    public String getGreetingMessage() {
        return getText(By.id("nameofuser"));
    }
}