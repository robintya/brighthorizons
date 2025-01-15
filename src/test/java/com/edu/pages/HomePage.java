package com.edu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edu.utility.SeleniumUtils;

public class HomePage extends SeleniumUtils {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // Locators
    private static final By ACCEPT_ALL_BUTTON = By.xpath("//button[text()='Manage Cookies']//..//button[text()='Accept All']");
    public static final By SEARCH_ICON = By.xpath("//nav[contains(@class,'js-nav-top')]//li[contains(@class,'displayed-desktop')]//span[contains(@class,'icon-search')]");
    public static final By SEARCH_FIELD = By.xpath("//nav[contains(@id,'search-desktop-top')]//input[@id='search-field']");
    public static final By SEARCH_BUTTON = By.xpath("//nav[contains(@id,'search-desktop-top')]//button[contains(@class,'btn-search')]");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void acceptCookies() {
        logger.info("Accepting cookies...");
		clickIfVisibleAndEnabled(driver, ACCEPT_ALL_BUTTON, 10);
    }

    public void clickSearchIcon() {
        logger.info("Clicking on the search icon...");
        clickElement(driver, SEARCH_ICON, 15);
    }

    public Boolean verifyIfElementExists() {
        logger.info("Checking if Search field exists");
        return isElementDisplayed(driver, SEARCH_FIELD, 10);
        }
    
    public void enterSearchText(String searchInputText) {
        logger.info("Entering search text: ", searchInputText);
		waitForElementAndEnterText(driver, SEARCH_FIELD, searchInputText, 0);
    }

    public void clickSearchButton() {
        logger.info("Clicking on the search button...");
		clickElement(driver, SEARCH_BUTTON, 0);
    }
}
