package com.edu.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.factory.DriverFactory;
import java.time.Duration;
import java.util.List;

public class SeleniumUtils extends DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	public static void clickIfVisibleAndEnabled(WebDriver driver, By locator, int timeoutSecs) {
		try {
			WebElement element;
			if (timeoutSecs > 0) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			} else {
				element = driver.findElement(locator);
			}

			// Check if the element is enabled and click on it
			if (element.isEnabled()) {
				element.click();
			} else {
				logger.info("Element is not enabled.");
			}
		} catch (Exception e) {
			logger.error("Failed to click the element: " + e.getMessage());
		}
	}

	public static void clickElement(WebDriver driver, By locator, int timeoutSecs) {
		try {
			WebElement element;
			if (timeoutSecs > 0) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			} else {
				element = driver.findElement(locator);
			}
			element.click();
		} catch (Exception e) {
			logger.error("Failed to click the element: " + e.getMessage());
			throw e;
		}
	}

	public static void waitForElementAndEnterText(WebDriver driver, By locator, String text, int timeoutSecs) {
		try {
			WebElement element;
			if (timeoutSecs > 0) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			} else {
				element = driver.findElement(locator);
			}
			// Clear the element and enter the text
			element.clear();
			element.sendKeys(text);
			logger.info("Text entered successfully: " + text);
		} catch (Exception e) {
			logger.error("Error entering text: " + e.getMessage());
		}
	}

	public static boolean isElementDisplayed(WebDriver driver, By locator, int timeoutSecs) {
		try {
			if (timeoutSecs > 0) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			} else {
				WebElement element = driver.findElement(locator);
				if (element.isDisplayed()) {
					logger.info("Element is visible: " + locator);
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Element is not visible: " + locator + " - " + e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean isElementListNonEmpty(WebDriver driver, By locator, int timeoutSecs) {
		try {
			List<WebElement> elements;

			if (timeoutSecs > 0) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
				elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			} else {
				elements = driver.findElements(locator);
			}

			// Check if the list size is greater than 0
			if (!elements.isEmpty()) {
				logger.info("List contains " + elements.size() + " element(s).");
				return true;
			} else {
				logger.info("List is empty.");
				return false;
			}
		} catch (Exception e) {
			logger.error("Error checking list size: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean verifyTextExists(WebDriver driver, By locator, String expectedText, int timeoutSecs) {
		try {
			List<WebElement> elements;

			if (timeoutSecs > 0) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
				elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			} else {
				elements = driver.findElements(locator);
			}

			for(WebElement element : elements) {
				String actualText = element.findElement(By.xpath(".//h3[@class='title']")).getText();
				if(actualText.trim().equals(expectedText)) {
					return true;
				}
			}
						
		} catch (Exception e) {
			logger.error("Error checking list size: " + e.getMessage());
			return false;
		}
		return false;
	}
}