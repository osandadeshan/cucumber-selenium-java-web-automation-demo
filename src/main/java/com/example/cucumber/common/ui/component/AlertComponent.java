package com.example.cucumber.common.ui.component;

import com.example.cucumber.page.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class AlertComponent extends BasePage {
    public AlertComponent(WebDriver driver) {
        super(driver);
    }

    public Alert switchToAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }
}
