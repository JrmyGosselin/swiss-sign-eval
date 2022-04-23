package com.jgo.swisssigneval.stepdef;

import com.jgo.swisssigneval.pageobjects.swisspost.SwissPostLoginPage;
import com.jgo.swisssigneval.pageobjects.swisspost.SwissPostMainPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class SwissPostStepdefs {
    SwissPostMainPage swissPostMainPage;
    SwissPostLoginPage swissPostLoginPage;

    @When("I access the SwissPost main page")
    public void go_to_swiss_post() {
        swissPostMainPage = SwissPostMainPage.reach();
    }

    @When("I click on the login button")
    public void click_login_button() {
        swissPostLoginPage = swissPostMainPage.goToLoginPage();
    }

    @When("I click on the SwissID login button")
    public void click_swiss_id_login_button() {
        SwissIdStepdefs.setSwissIdLoginPage(swissPostLoginPage.loginWithSwissId());
    }

    @When("I log out")
    public void log_out() {
        swissPostMainPage.logout();
    }

    @Then("I am successfully logged out")
    public void is_logged_out() {
        assertTrue(swissPostMainPage.isLoggedOut());
    }
}
