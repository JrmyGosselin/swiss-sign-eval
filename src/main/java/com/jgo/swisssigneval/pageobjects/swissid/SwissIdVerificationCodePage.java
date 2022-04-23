package com.jgo.swisssigneval.pageobjects.swissid;

import com.jgo.swisssigneval.pageobjects.swisspost.SwissPostMainPage;
import com.jgo.swisssigneval.selenium.GeckoSeleniumClient;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwissIdVerificationCodePage {
    @FindBy(id = "registration-email-confirm")
    WebElement codeVerificationForm;

    public SwissPostMainPage enterCodeAndCompleteRegistration(String code) {
        codeVerificationForm.findElement(By.id("codeBox")).sendKeys(code);

        WebDriver webDriver = GeckoSeleniumClient.getInstance().getWebDriver();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,
                10);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmed"))).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("acceptShareData"))).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("b2cButton"))).click();

        WebElement element = webDriver.findElement(By.id("agb"));
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", element);

        webDriver.findElement(By.id("complete")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nextButton"))).click();

        return PageFactory.initElements(webDriver, SwissPostMainPage.class);
    }
}
