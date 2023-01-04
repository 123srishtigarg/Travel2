package com.travel.core;

import com.travel.factory.BrowserFactory;
import com.travel.utilities.JsonUtility;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase {

    Object object = new Object();
    public WebDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        ReadConfig.loadConfigFile();
        driver = setUpBrowser();
        launchURL();

    }

    public WebDriver setUpBrowser() throws Exception {

        String  executionType = ReadConfig.readConfigFile("ExecutionEnvironment");
        BrowserFactory factory = new BrowserFactory();

        if (executionType.equalsIgnoreCase("Local")) {
            driver = factory.createLocalBrowserInstance();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        return driver;
    }

    public void launchURL() {
        driver.get(getEnvironmentURL());
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public String getEnvironmentURL() {

        String   environment = ReadConfig.readConfigFile("URL");
        return environment;
    }

    public Object getDataMap(String JsonFileName){
        object = JsonUtility.initObjectRepository(JsonFileName);
        return object;
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

}
