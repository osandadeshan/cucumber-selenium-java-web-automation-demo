package com.example.cucumber.util.driver;

import java.security.InvalidParameterException;

import static com.example.cucumber.common.constant.Browser.*;
import static com.example.cucumber.common.constant.CommonConstants.BROWSER_NAME;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class WebDriverFactory {
    public WebDriverService getDriverService() {
        WebDriverService driver;

        switch (BROWSER_NAME) {
            case CHROME:
                driver = new ChromeDriverServiceImpl();
                break;
            case HEADLESS_CHROME:
                driver = new HeadlessChromeDriverServiceImpl();
                break;
            case FIREFOX:
                driver = new FirefoxDriverServiceImpl();
                break;
            case HEADLESS_FIREFOX:
                driver = new HeadlessFirefoxDriverServiceImpl();
                break;
            case EDGE:
                driver = new EdgeDriverServiceImpl();
                break;
            case IE:
                driver = new InternetExplorerDriverServiceImpl();
                break;
            case SAFARI:
                driver = new SafariDriverServiceImpl();
                break;

            default:
                throw new InvalidParameterException("'" + BROWSER_NAME + "' browser is not implemented");
        }

        return driver;
    }
}
