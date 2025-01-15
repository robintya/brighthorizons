package com.edu.tests.smoke;

import com.edu.base.BaseTest;
import com.edu.config.TestInputs;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Verify_Search extends BaseTest {

    @Test
    public void verifySearchFunctionality() {
    	boolean isPresent;
        try {
            // Perform search
        	pageObjectManager.getHomePage().clickSearchIcon();
        	isPresent = pageObjectManager.getHomePage().verifyIfElementExists();
            Assert.assertTrue(isPresent, "Search field verification failed!");

            pageObjectManager.getHomePage().enterSearchText(TestInputs.SEARCH_INPUT_TEXT);
            pageObjectManager.getHomePage().clickSearchButton();

            // Verify search results
            isPresent = pageObjectManager.getSearchResultsPage()
                    .verifySearchResults(TestInputs.SEARCH_INPUT_TEXT);
            Assert.assertTrue(isPresent, "Search results verification failed!");
        } catch (Exception e) {
            Assert.fail("Test execution failed due to an exception: " + e.getMessage());
        }
    }
}