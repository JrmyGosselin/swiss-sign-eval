package com.jgo.swisssigneval.selenium;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.concurrent.TimeUnit;

public class GeckoSeleniumClient {
    private static final String GECKO_SYSTEM_PROPERTY_KEY = "webdriver.gecko.driver";

    private static final String GECKO_DRIVER_FOLDER = "selenium/gecko";

    private static final String WIN_GECKO_PATH = "geckodriver.exe";
    private static final String LINUX_GECKO_PATH= "geckodriver-linux";
    private static final String MACOS_GECKO_PATH = "geckodriver-macos";

    private static GeckoSeleniumClient instance;

    private WebDriver webDriver;

    private GeckoSeleniumClient(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void shutdown() {
        webDriver.quit();
        instance = null;
    }

    public static GeckoSeleniumClient getInstance() {
        try {
            if(instance == null) {
                FirefoxOptions capabilities = new FirefoxOptions();

                Proxy proxy = new Proxy();
                proxy.setProxyType(Proxy.ProxyType.DIRECT);
                capabilities.setProxy(proxy);

                setGeckoSystemProperty();

                instance = new GeckoSeleniumClient(new FirefoxDriver(capabilities));
            }
            return instance;
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private static void setGeckoSystemProperty() throws IOException, URISyntaxException {
        if (System.getProperty(GECKO_SYSTEM_PROPERTY_KEY) == null) {
            System.setProperty(GECKO_SYSTEM_PROPERTY_KEY, getGeckoDriver().toString());
        }
    }

    // This returns the right version of the gecko drivers depending on your OS
    private static Path getGeckoDriver() throws URISyntaxException, IOException {
        Path driverPath = Paths.get(GeckoSeleniumClient.class.getClassLoader().getResource(GECKO_DRIVER_FOLDER).toURI());
        if (SystemUtils.IS_OS_WINDOWS) {
            driverPath = driverPath.resolve(WIN_GECKO_PATH);
        } else {
            if (SystemUtils.IS_OS_MAC) {
                driverPath = driverPath.resolve(MACOS_GECKO_PATH);
            } else if (SystemUtils.IS_OS_LINUX) {
                driverPath = driverPath.resolve(LINUX_GECKO_PATH);
            } else {
                throw new UnsupportedOperationException(
                        "We couldn't find an adequate geckodriver for your operating system : " + SystemUtils.OS_NAME);
            }

            // in UNIX-like systems, we must set the posix file permissions in order to execute the driver
            Files.setPosixFilePermissions(driverPath,
                    ImmutableSet.of(PosixFilePermission.OWNER_READ,
                            PosixFilePermission.OWNER_WRITE,
                            PosixFilePermission.OWNER_EXECUTE,
                            PosixFilePermission.GROUP_EXECUTE,
                            PosixFilePermission.OTHERS_EXECUTE));
        }
        return driverPath;
    }

}
