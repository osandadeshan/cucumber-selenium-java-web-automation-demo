package com.example.cucumber.common.constant;

import static com.example.cucumber.common.constant.ApplicationConstants.*;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:25 PM
 * Description     :
 **/

public enum User {
    WORKER(WORKER_LOGIN_USERNAME, WORKER_LOGIN_PASSWORD),
    ADMIN(ADMIN_LOGIN_USERNAME, ADMIN_LOGIN_PASSWORD),
    SUPER_ADMIN(SUPER_ADMIN_LOGIN_USERNAME, SUPER_ADMIN_LOGIN_PASSWORD);

    private final String username, password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
