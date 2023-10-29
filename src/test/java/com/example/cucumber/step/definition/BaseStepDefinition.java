package com.example.cucumber.step.definition;

import com.example.cucumber.common.PageProvider;
import com.example.cucumber.common.UiComponentProvider;
import com.example.cucumber.util.driver.WebDriverFactory;
import com.example.cucumber.util.driver.WebDriverService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.example.cucumber.common.constant.ApplicationConstants.APPLICATION_URL;
import static com.example.cucumber.util.driver.WebDriverHolder.getDriver;
import static com.example.cucumber.util.driver.WebDriverHolder.setDriver;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class BaseStepDefinition {
    private final WebDriverService driverService = new WebDriverFactory().getDriverService();

    @Before
    public void openBrowser() {
        driverService.spinUpDriver();
        setDriver(driverService.getDriver());
        getDriver().get(APPLICATION_URL);
    }

    @After()
    public void takeScreenshotAndCloseBrowser(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driverService.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot on failure");
        }
        driverService.closeDriver();
    }

    public static PageProvider pages() {
        return new PageProvider(getDriver());
    }

    public static UiComponentProvider uiComponents() {
        return new UiComponentProvider(getDriver());
    }
}
