package com.travel.factory;

import com.travel.core.ReadConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

public class BrowserFactory {
    WebDriver driver = null;

    public WebDriver createLocalBrowserInstance() {

        String browser = getBrowserName();

        if (browser.equalsIgnoreCase("Chrome")) {

            WebDriverManager.chromedriver().setup();

            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("Edge")) {

            WebDriverManager.edgedriver().setup();

            driver = new EdgeDriver();

        } else {

            System.out.println( browser + "- Local Browser is NOT Supported. Please use any of the browser from following for you test execution - Chrome, Edge");
            Assert.assertTrue(false);
        }
        return driver;
    }

    public String getBrowserName() {
        String browser = System.getProperty("browser");

        if (browser == null) {
            browser = ReadConfig.readConfigFile("BrowserName");
        }

        return browser;
    }
}
