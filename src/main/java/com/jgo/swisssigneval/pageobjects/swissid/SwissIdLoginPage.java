package com.jgo.swisssigneval.pageobjects.swissid;

import com.jgo.swisssigneval.selenium.GeckoSeleniumClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwissIdLoginPage {
    @FindBy(id = "CREATE_ACCOUNT")
    private WebElement createAccountButton;

    public SwissIdRegistrationPage goToAccountCreationForm() {
        createAccountButton.findElement(By.tagName("button")).click();

        return PageFactory.initElements(GeckoSeleniumClient.getInstance().getWebDriver(),
                SwissIdRegistrationPage.class);
    }
}
