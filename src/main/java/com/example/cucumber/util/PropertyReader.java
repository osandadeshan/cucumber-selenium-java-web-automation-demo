package com.example.cucumber.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.example.cucumber.common.constant.CommonConstants.EXECUTION_ENV_NAME;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class PropertyReader {
    private final static Logger logger = LogManager.getLogger();

    public static String getEnvironmentConfig(String propertyName) {
        return getPropertyValue(("env/" + EXECUTION_ENV_NAME + ".properties"), propertyName);
    }

    private static String getPropertyValue(String filename, String propertyName) {
        String propertyValue = null;

        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(filename)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException ex) {
            logger.error(filename + " file cannot be found!");
            ex.printStackTrace();
        }

        return propertyValue;
    }
}
