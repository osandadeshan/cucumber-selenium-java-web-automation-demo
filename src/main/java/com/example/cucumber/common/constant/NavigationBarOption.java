package com.example.cucumber.common.constant;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public enum NavigationBarOption {
    HOME("Home"),
    CONTACT("Contact"),
    ABOUT_US("About us"),
    CART("Cart"),
    LOG_IN("Log in"),
    SIGN_UP("Sign up");

    private final String navBarItemName;

    NavigationBarOption(String navBarItemName) {
        this.navBarItemName = navBarItemName;
    }

    public String getName() {
        return navBarItemName;
    }
}
