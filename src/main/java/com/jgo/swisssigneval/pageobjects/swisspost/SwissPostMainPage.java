package com.jgo.swisssigneval.pageobjects.swisspost;

import com.jgo.swisssigneval.selenium.GeckoSeleniumClient;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class SwissPostMainPage {
    private static final String URL = "https://www.post.ch/en";

    @FindBy(id = "klpLoginWidget")
    private WebElement loginWidget;

    public SwissPostLoginPage goToLoginPage() {
        loginWidget.findElement(By.tagName("a")).click();

        return PageFactory.initElements(GeckoSeleniumClient.getInstance().getWebDriver(), SwissPostLoginPage.class);
    }

    public static SwissPostMainPage reach() {
        WebDriver webDriver = GeckoSeleniumClient.getInstance().getWebDriver();

        webDriver.get(URL);

        return PageFactory.initElements(webDriver, SwissPostMainPage.class);
    }

    public void logout() {
        loginWidget.findElement(By.className("klp-widget-authenticated-session-link")).click();

        loginWidget.findElement(By.id("klp-widget-authenticated-menu-logout")).click();
    }

    public boolean isLoggedOut() {
        return loginWidget.findElement(By.className("klp-widget-anonymous__text")).isDisplayed();
    }
}
