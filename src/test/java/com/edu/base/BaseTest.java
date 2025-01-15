package com.edu.base;

import com.edu.config.BrowserType;
import com.edu.config.Urls;
import com.edu.pages.PageObjectManager;
import com.edu.pages.SearchResultsPage;
import com.edu.utility.SeleniumUtils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest extends SeleniumUtils {

    protected WebDriver driver;
    protected PageObjectManager pageObjectManager;
    private static final Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);

    @BeforeSuite
    public void checkServerHealth() {
        String url = Urls.BASE_URL.getUrl(); // Replace with your actual URL
        int statusCode = getStatusCode(url);

        if (statusCode != 200) {
        	logger.error("Server health check failed. Status code: " + statusCode);
            throw new SkipException("Tests skipped because the server is not healthy (Status code: " + statusCode + ")");
        }

        logger.info("Server is healthy. Status code: " + statusCode);
    }
    
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
