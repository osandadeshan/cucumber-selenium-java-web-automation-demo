package com.example.cucumber.common;

import com.example.cucumber.common.ui.component.AlertComponent;
import com.example.cucumber.page.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class UiComponentProvider extends BasePage {
    public UiComponentProvider(WebDriver driver) {
        super(driver);
    }

    public AlertComponent getAlertComponent() {
        return new AlertComponent(driver);
    }
}
