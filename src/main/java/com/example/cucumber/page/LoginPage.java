package com.example.cucumber.page;

import com.example.cucumber.common.constant.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickLoginButton();
    }

    public void login(User user) {
        login(user.getUsername(), user.getPassword());
    }

    private void inputUsername(String email) {
        sendKeys(By.id("loginusername"), email);
    }

    private void inputPassword(String password) {
        sendKeys(By.id("loginpassword"), password);
    }

    private void clickLoginButton() {
        click(By.xpath("//button[@onclick='logIn()']"));
    }
}