package com.example.cucumber.step.definition;

import com.example.cucumber.common.PageProvider;
import com.example.cucumber.common.UiComponentProvider;

import static com.example.cucumber.util.driver.WebDriverHolder.getDriver;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 31/10/23
 * Time            : 12:59 AM
 * Description     :
 **/

public class UIProvider {
    public static PageProvider pages() {
        return new PageProvider(getDriver());
    }

    public static UiComponentProvider uiComponents() {
        return new UiComponentProvider(getDriver());
    }
}
