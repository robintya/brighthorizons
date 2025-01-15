package com.edu.pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    // Constructor to initialize WebDriver
    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    // Getter for HomePage
    public HomePage getHomePage() {
        return homePage == null ? (homePage = new HomePage(driver)) : homePage;
    }

    // Getter for SearchResultsPage
    public SearchResultsPage getSearchResultsPage() {
        return searchResultsPage == null ? (searchResultsPage = new SearchResultsPage(driver)) : searchResultsPage;
    }
}
