package com.jgo.swisssigneval.pageobjects.swisspost;

import com.jgo.swisssigneval.pageobjects.swissid.SwissIdLoginPage;
import com.jgo.swisssigneval.selenium.GeckoSeleniumClient;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwissPostLoginPage {
    @FindBy(id = "externalIDP")
    private WebElement loginWithSwissIdButton;

    public SwissIdLoginPage loginWithSwissId() {
        loginWithSwissIdButton.click();

        return PageFactory.initElements(GeckoSeleniumClient.getInstance().getWebDriver(), SwissIdLoginPage.class);
    }
}
