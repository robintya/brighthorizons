package com.edu.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.config.TestInputs;
import com.edu.config.Timeouts;
import com.edu.utility.SeleniumUtils;

public class FindCenterPage extends SeleniumUtils {

	private WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(FindCenterPage.class);

	// Locators
	private static final By ADDRESS_INPUT_FIELD = By.cssSelector("input#addressInput");
	private static final By TOTAL_CENTERS = By.cssSelector("span.resultsNumber");
	private static final By INDIVIDUAL_CENTERS = By.cssSelector("div[class*='centerResult infoWindow']");
	private static final By TOOLTIP_CENTER_NAME = By.xpath("//span[@class='mapTooltip__headline']");
	private static final By TOOLTIP_CENTER_ADDRESS = By.cssSelector("div.mapTooltip__address");

	// Constructor
	public FindCenterPage(WebDriver driver) {
		this.driver = driver;
	}

	// Checks if Url contains endpoint
	public boolean doesUrlContain() {
		logger.info("Checking if Url contains the endpoint : " + TestInputs.FIND_CENTER_ENDPOINT);
		return verifyURL(driver, Timeouts.MEDIUM);  
	}

	// Inputs text into the Address field sequentially 
	public void enterAddressSequentially(String addressInputText) {
		logger.info("Entering search text: ", addressInputText);
		typeTextSequentially(driver, ADDRESS_INPUT_FIELD, addressInputText, Timeouts.DEFAULT);
	}

	// Press Enter key on the Address field
	public void pressEnterOnAddressFiled() {
		logger.info("Pressing Enter");
		pressEnter(driver, ADDRESS_INPUT_FIELD, Timeouts.DEFAULT);
	}

	// Verify if no of total centers match against no of centers in the list
	public boolean isTotalMatchingCenters() {
		try {
			logger.info("Verify if no of total centers match against no of centers in the list");

			waitForTextUntilMatch(driver,INDIVIDUAL_CENTERS, By.cssSelector("span.centerResult__address"), TestInputs.ADDRESS_INPUT_TEXT, Timeouts.LONG, TestInputs.CENTER_POSITION);

			// Wait for the total centers element to be visible
			WebElement totalCenterElement = waitForElementToBeVisible(driver, TOTAL_CENTERS, Timeouts.DEFAULT);

			// Parse the total centers count
			int totalCenters = Integer.parseInt(totalCenterElement.getText().trim());

			// Wait for the individual center elements to be visible
			List<WebElement> individualCentersElement = waitForElementsToBeVisible(driver, INDIVIDUAL_CENTERS, Timeouts.DEFAULT);

			// Compare the total centers count with the number of individual centers
			return totalCenters == individualCentersElement.size();

		} catch (Exception e) {
			logger.error("Error in verifying total matching centers. Locator: " + TOTAL_CENTERS + " or " + INDIVIDUAL_CENTERS, e.getMessage());
			return false;
		}
	}

	// Clicks on the nth center element in the list
	public void clickOnSpecificCenterElement(int position) {

		try {

			logger.info("Clicks on the nth center element in the list");

			List<WebElement> individualCenters = waitForElementsToBeVisible(driver, INDIVIDUAL_CENTERS, Timeouts.DEFAULT);

			if (!individualCenters.isEmpty()) {
				WebElement centerElement = individualCenters.get(position-1);

				clickElement(driver, centerElement, Timeouts.DEFAULT);
			} else {
				throw new RuntimeException("No centers found to click.");
			}
		}catch(Exception e) {
			logger.error("Error while clicking on the nth center element in the list", e.getMessage());
		}
	}

	// Returns nth center element from the list
	public WebElement getSpecificCenterElement(int position) {
		try {
			logger.info("Returns nth center element from the list");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeouts.DEFAULT));

			List<WebElement> individualCenters = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(INDIVIDUAL_CENTERS));

			if (!individualCenters.isEmpty()) {
				return individualCenters.get(position - 1);
			} else {
				throw new RuntimeException("No centers found.");
			}
		}catch(Exception e) {
			logger.error("Error while returning nth center element from the list", e.getMessage());
		}
		return null;
	}

	// Compare the values from the nth center vs. values in the tooltip
	public boolean validateCenterPropertiesWithTooltip(int position) {

		try {
			logger.info("Comparing the values from the nth center vs. values in the tooltip");

			// Get name and address of the nth center
			WebElement centerElement = getSpecificCenterElement(position);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeouts.DEFAULT));
			WebElement centerElementName = wait.until(ExpectedConditions.visibilityOf(centerElement.findElement(By.cssSelector("h3.centerResult__name"))));

			String specificCenterName = centerElementName.getText().trim();

			WebElement centerElementAddress = wait.until(ExpectedConditions.visibilityOf(centerElement.findElement(By.cssSelector("span.centerResult__address"))));
			String specificCenterAddress = centerElementAddress.getText().trim();

			// Get name and address from the right-side tooltip
			String toolTipCenterName = getElementText(driver, TOOLTIP_CENTER_NAME, Timeouts.DEFAULT);
			String toolTipCenterAddress = getElementText(driver, TOOLTIP_CENTER_ADDRESS, Timeouts.DEFAULT).replace("\n", " ");

			// Compare the values
			return specificCenterName.equals(toolTipCenterName) && specificCenterAddress.equals(toolTipCenterAddress);
		}catch(Exception e) {
			logger.error("Error while comparing the values from the nth center vs. values in the tooltip", e.getMessage());
		}
		return false;
	}

}
