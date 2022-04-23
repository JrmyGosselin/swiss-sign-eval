package com.jgo.swisssigneval.pageobjects.swissid;

import com.jgo.swisssigneval.selenium.GeckoSeleniumClient;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class SwissIdRegistrationPage {
    @FindBy(id = "sws-registration")
    private WebElement registrationForm;

    public void fillForm(String email) {
        // Select salutation as Ms.
        registrationForm.findElement(By.id("salutation"))
                .findElements(By.tagName("label"))
                .get(0)
                .click();

        // First name will always be Marie
        registrationForm.findElement(By.id("firstName"))
                .sendKeys("Marie");

        // Last name will always be Dupont-Swisssign
        registrationForm.findElement(By.id("lastName"))
                .sendKeys("Dupont-Swisssign");

        // Email will be filled with the parameterized value (i.e. a throwaway)
        registrationForm.findElement(By.id("email"))
                .sendKeys(email);

        // Password will be swisssign_10
        String password = "swisssign_10";
        registrationForm.findElement(By.id("newPassword"))
                .sendKeys(password);

        registrationForm.findElement(By.id("confirmPassword"))
                .sendKeys(password);

        // Accept the GTC
        registrationForm.findElement(By.id("wrapper-gtc"))
                .findElement(By.tagName("label"))
                .click();
    }

    public SwissIdVerificationCodePage submitForm() {
        registrationForm.findElement(By.id("nextButton")).click();

        // In order to make the form work we need to perform a little "dance", as the salutation field bugs out somehow
        registrationForm.findElement(By.id("salutation"))
                .findElements(By.tagName("label"))
                .get(1)
                .click();

        registrationForm.findElement(By.id("salutation"))
                .findElements(By.tagName("label"))
                .get(0)
                .click();

        registrationForm.findElement(By.id("nextButton")).click();

        return PageFactory.initElements(GeckoSeleniumClient.getInstance().getWebDriver(),
                SwissIdVerificationCodePage.class);
    }

}
