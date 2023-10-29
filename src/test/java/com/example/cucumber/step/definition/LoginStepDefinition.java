package com.example.cucumber.step.definition;

import com.example.cucumber.common.constant.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.example.cucumber.common.constant.NavigationBarOption.LOG_IN;
import static com.example.cucumber.step.definition.BaseStepDefinition.pages;
import static com.example.cucumber.step.definition.BaseStepDefinition.uiComponents;
import static org.junit.Assert.assertEquals;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class LoginStepDefinition {
    @Given("I am on the Login page")
    public void navigateToLogin() {
        pages().getNavigationBar()
                .clickOnNavOption(LOG_IN);
    }

    @When("I login with username {string} and password {string}")
    public void loginWithUsernameAndPassword(String username, String password) {
        pages().getLoginPage()
                .login(username, password);
    }

    @When("I login with {string} user")
    public void login(String user) {
        pages().getLoginPage()
                .login(User.valueOf(user));
    }

    @Then("The greeting message should visible with the {string}'s username")
    public void checkWelcomeMessage(String user) {
        assertEquals(
                "Welcome " + User.valueOf(user).getUsername(),
                pages().getNavigationBar().getGreetingMessage()
        );
    }

    @Then("An alert for incorrect login credentials should visible")
    public void checkAlertErrorMessage() {
        assertEquals(
                uiComponents().getAlertComponent().switchToAlert().getText(),
                "Wrong password."
        );
    }
}
