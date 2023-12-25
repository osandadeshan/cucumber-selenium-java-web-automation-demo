package com.example.cucumber.common.constant;

import static com.example.cucumber.util.PropertyReader.getEnvironmentConfig;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class ApplicationConstants {
    public static final String APPLICATION_URL = getEnvironmentConfig("application_url");
    public static final String WORKER_LOGIN_USERNAME = getEnvironmentConfig("worker_username");
    public static final String WORKER_LOGIN_PASSWORD = getEnvironmentConfig("worker_password");
    public static final String ADMIN_LOGIN_USERNAME = getEnvironmentConfig("admin_username");
    public static final String ADMIN_LOGIN_PASSWORD = getEnvironmentConfig("admin_password");
    public static final String SUPER_ADMIN_LOGIN_USERNAME = getEnvironmentConfig("super_admin_username");
    public static final String SUPER_ADMIN_LOGIN_PASSWORD = getEnvironmentConfig("super_admin_password");
}
