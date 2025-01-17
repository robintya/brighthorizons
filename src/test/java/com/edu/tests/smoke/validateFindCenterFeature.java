package com.edu.tests.smoke;

import com.edu.base.BaseTest;
import com.edu.config.TestInputs;
import com.edu.pages.FindCenterPage;
import com.edu.pages.HomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class validateFindCenterFeature extends BaseTest {

	@Test
	public void verifySearchFunctionality() {
		boolean isEndpointPresent, isCentersCountSame, doesCenterDetailsMatch;

		// Initialize page objects
		final HomePage homePage = pageObjectManager.getHomePage();
		final FindCenterPage findCenterPage = pageObjectManager.getFindCenterPage();

		try {
			// Moves to Find Center Page & Verify if Url contains endpoint
			homePage.clickFindCenterButton();
			isEndpointPresent = findCenterPage.doesUrlContain();
			Assert.assertTrue(isEndpointPresent, "URL: " + TestInputs.FIND_CENTER_ENDPOINT + " verification failed!");

			// Inputs text into the Address field & Press Enter 
			findCenterPage.enterAddressSequentially(TestInputs.ADDRESS_INPUT_TEXT);
			findCenterPage.pressEnterOnAddressFiled();
		
			// Verify no of total centers vs available in the list
			isCentersCountSame = findCenterPage.isTotalMatchingCenters();
			Assert.assertTrue(isCentersCountSame, "Centers count's result verification failed!");
			
			// Clicks on nth (first) Center in the list & Verify center's properties against tooltip's properties
			findCenterPage.clickOnSpecificCenterElement(TestInputs.CENTER_POSITION);
			doesCenterDetailsMatch = findCenterPage.validateCenterPropertiesWithTooltip(TestInputs.CENTER_POSITION);
			Assert.assertTrue(doesCenterDetailsMatch, "Center's properties verification failed!");
			
		} catch (Exception e) {
			Assert.fail("Test execution failed due to an exception, Error : " + e.getMessage());
		}
	}
}