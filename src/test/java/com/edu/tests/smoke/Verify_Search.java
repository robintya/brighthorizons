package com.edu.tests.smoke;

import com.edu.base.BaseTest;
import com.edu.config.TestInputs;
import com.edu.pages.HomePage;
import com.edu.pages.SearchResultsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Verify_Search extends BaseTest {

	@Test
	public void verifySearchFunctionality() {
		boolean isSearchFieldPresent;
		boolean isSearchResultsPresent;

		// Initialize page objects
		final HomePage homePage = pageObjectManager.getHomePage();
		final SearchResultsPage searchResultsPage = pageObjectManager.getSearchResultsPage();

		try {
			// Perform search
			homePage.clickSearchIcon();
			isSearchFieldPresent = homePage.verifyIfElementExists();
			Assert.assertTrue(isSearchFieldPresent, "Search field verification failed!");

			homePage.enterSearchText(TestInputs.SEARCH_INPUT_TEXT);
			homePage.clickSearchButton();

			// Verify search results
			isSearchResultsPresent = searchResultsPage.verifySearchResults(TestInputs.SEARCH_INPUT_TEXT);
			Assert.assertTrue(isSearchResultsPresent, "Search results verification failed!");
		} catch (Exception e) {
			Assert.fail("Test execution failed due to an exception: " + e.getMessage());
		}
	}
}