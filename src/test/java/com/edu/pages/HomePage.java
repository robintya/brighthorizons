package com.edu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.config.Timeouts;
import com.edu.utility.SeleniumUtils;

public class HomePage extends SeleniumUtils {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // Locators
    private static final By ACCEPT_ALL_BUTTON = By.xpath("//button[text()='Manage Cookies']//..//button[text()='Accept All']");
    private static final By FIND_CENTER_BUTTON = By.xpath("  //nav[contains(@class,'js-nav-top')]//li[contains(@class,'displayed-desktop')]//a[contains(@data-tracking-cta,'Find a Center')]");
    public static final By SEARCH_ICON = By.xpath("//nav[contains(@class,'js-nav-top')]//li[contains(@class,'displayed-desktop')]//span[contains(@class,'icon-search')]");
    public static final By SEARCH_FIELD = By.xpath("//nav[contains(@id,'search-desktop-top')]//input[@id='search-field']");
    public static final By SEARCH_BUTTON = By.xpath("//nav[contains(@id,'search-desktop-top')]//button[contains(@class,'btn-search')]");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Handles and accepts the cookies pop-up if present
    public void acceptCookies() {
        logger.info("Accepting cookies...");
		clickIfVisibleAndEnabled(driver, ACCEPT_ALL_BUTTON, Timeouts.DEFAULT);
    }
    
    // Clicks on the Search Icon on the top right corner
    public void clickSearchIcon() {
        logger.info("Clicking on the search icon...");
        clickElement(driver, SEARCH_ICON, Timeouts.DEFAULT);
    }
    
    // Clicks on Find a Center button
    public void clickFindCenterButton() {
        logger.info("Clicking on the find center button...");
        clickElement(driver, FIND_CENTER_BUTTON, Timeouts.DEFAULT);
    }
    
    // Checks if the Search field is present on the page
    public Boolean verifyIfElementExists() {
        logger.info("Checking if Search field exists");
        return isElementDisplayed(driver, SEARCH_FIELD, Timeouts.DEFAULT);
        }
    
    // Inputs text into the Search field
    public void enterSearchText(String searchInputText) {
        logger.info("Entering search text: ", searchInputText);
		waitForElementAndEnterText(driver, SEARCH_FIELD, searchInputText, Timeouts.ZERO);
    }

    // Clicks on the Search button
    public void clickSearchButton() {
        logger.info("Clicking on the search button...");
		clickElement(driver, SEARCH_BUTTON, Timeouts.ZERO);
    }
}
