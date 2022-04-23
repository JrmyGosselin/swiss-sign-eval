package com.jgo.swisssigneval.hooks;

import com.jgo.swisssigneval.selenium.GeckoSeleniumClient;
import io.cucumber.java.After;

public class SeleniumHooks {
    @After
    public void shutdownSelenium() {
        GeckoSeleniumClient.getInstance().shutdown();
    }
}
