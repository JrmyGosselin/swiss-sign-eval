package com.jgo.swisssigneval.stepdef;

import com.jgo.swisssigneval.pageobjects.swissid.SwissIdLoginPage;
import com.jgo.swisssigneval.pageobjects.swissid.SwissIdRegistrationPage;
import com.jgo.swisssigneval.pageobjects.swissid.SwissIdVerificationCodePage;
import io.cucumber.java.en.When;

public class SwissIdStepdefs {
    private static SwissIdLoginPage swissIdLoginPage;
    private static SwissIdRegistrationPage swissIdRegistrationPage;

    private static SwissIdVerificationCodePage swissIdVerificationCodePage;

    @When("I register a new SwissID account for the unregistered account")
    public void register_new_swiss_id_account() {
        swissIdRegistrationPage = swissIdLoginPage.goToAccountCreationForm();
        swissIdRegistrationPage.fillForm(EmailStepdefs.mailProvider.getThrowAwayEmail());
        swissIdVerificationCodePage = swissIdRegistrationPage.submitForm();
    }

    @When("I complete registration as a private user with the provided code received via email")
    public void follow_through_registration_with_code() {
        String code = EmailStepdefs.mailProvider.getCode();
        swissIdVerificationCodePage.enterCodeAndCompleteRegistration(code);
    }

    public static void setSwissIdLoginPage(SwissIdLoginPage swissIdLoginPage) {
        SwissIdStepdefs.swissIdLoginPage = swissIdLoginPage;
    }
}
