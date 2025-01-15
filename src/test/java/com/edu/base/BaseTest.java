package com.edu.base;

import com.edu.config.BrowserType;
import com.edu.config.Urls;
import com.edu.pages.PageObjectManager;
import com.edu.utility.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest extends SeleniumUtils {

    protected WebDriver driver;
    protected PageObjectManager pageObjectManager;

    @BeforeMethod
    public void setUp() {
    	setDriver(BrowserType.CHROME);
    	driver = getDriver();
        pageObjectManager = new PageObjectManager(driver);
        // Navigate to URL
        driver.get(Urls.BASE_URL.getUrl());
        pageObjectManager.getHomePage().acceptCookies();
    }

    @AfterMethod
    public void tearDown() {
        quitDriver();
    }
}
