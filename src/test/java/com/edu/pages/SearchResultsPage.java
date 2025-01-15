package com.edu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.config.TestInputs;
import com.edu.utility.SeleniumUtils;

public class SearchResultsPage extends SeleniumUtils {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);

    // Locators
    public static final By RESULTS_CONTAINER = By.cssSelector("div.results.container div.col");

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public boolean verifySearchResults(String expectedText) {
        logger.info("Verifying search results for text: ", expectedText);
        boolean isPresent = verifyTextExists(driver, RESULTS_CONTAINER, TestInputs.SEARCH_INPUT_TEXT, 30);
        logger.info("Search result presence: ", isPresent);
        return isPresent;
    }
}
