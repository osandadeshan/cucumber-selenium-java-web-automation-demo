package com.example.cucumber.util.driver;

import org.openqa.selenium.WebDriver;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class WebDriverHolder {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        WebDriverHolder.driver.set(driver);
    }
}
