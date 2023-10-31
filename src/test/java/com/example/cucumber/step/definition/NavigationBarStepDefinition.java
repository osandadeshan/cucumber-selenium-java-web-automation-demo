package com.example.cucumber.step.definition;

import com.example.cucumber.common.constant.NavigationBarOption;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.example.cucumber.common.constant.ApplicationConstants.APPLICATION_URL;
import static com.example.cucumber.step.definition.UIProvider.pages;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Project Name    : cucumber-selenium-java-web-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 29/10/23
 * Time            : 8:19 PM
 * Description     :
 **/

public class NavigationBarStepDefinition {
    private String navigationBarOption;

    @Given("The Navigation Bar is on the top of the page")
    public void checkNavigationBarVisibility() {
        pages().getNavigationBar()
                .checkNavigationBarVisibility();
    }

    @When("User checks the Navigation Bar option {string}")
    public void setNavigationBarOption(String navigationBarOption) {
        this.navigationBarOption = navigationBarOption;
    }

    @Then("The user should see the Navigation Bar option")
    public void checkNavigationBarOptionIsVisible() {
        assertTrue(
                pages().getNavigationBar()
                        .getNavigationOptionElement(NavigationBarOption.valueOf(navigationBarOption))
                        .isDisplayed()
        );
    }

    @Then("The user should see the Navigation Bar option routes to {string}")
    public void checkNavigationBarOptionRoutine(String url) {
        assertEquals(
                APPLICATION_URL + url,
                pages().getNavigationBar()
                        .getNavigationOptionElement(NavigationBarOption.valueOf(navigationBarOption))
                        .getAttribute("href")
        );
    }
}
