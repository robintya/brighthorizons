package com.edu.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.config.TestInputs;
import com.edu.config.Timeouts;
import com.edu.factory.DriverFactory;

import java.net.HttpURLConnection;
import java.net.URL;
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
	
	public static void clickElement(WebDriver driver, WebElement element, int timeoutSecs) {
	    try {
	        if (timeoutSecs > 0) {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
	            wait.until(ExpectedConditions.elementToBeClickable(element));
	        }
	        // Click the WebElement
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
	
	public int getStatusCode(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); // Use HEAD for a lightweight check
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            logger.error("Error while checking server status: " + e.getMessage());
            return -1; // Return an invalid status code to indicate failure
        }
    }
	
	public static void pressEnter(WebDriver driver, By locator, int timeoutSecs) {
		WebElement element = null;
        try {
        	element = waitForElementToBeVisible(driver, locator, timeoutSecs);
        	element.sendKeys(Keys.ENTER);
        	Thread.sleep(Timeouts.CUSTOM);
		}catch (Exception e) {
	        logger.error("Failed to press Enter key on the Address field" + e.getMessage());
		} 
    }
	
	public static String getElementText(WebDriver driver, By locator, int timeoutSecs) {
	    try {
	        WebElement element;

	        // Wait for the element to be visible before getting its text
	        if (timeoutSecs > 0) {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
	            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	        } else {
	            element = driver.findElement(locator);
	        }

	        // Return the text of the element
	        return element.getText().trim(); // Trims extra spaces or newlines
	    } catch (Exception e) {
	        logger.error("Failed to retrieve text from the element located by: " + locator + ". Error: " + e.getMessage());
	        throw e;
	    }
	}
	
	public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutSecs) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            throw new RuntimeException("Element located by " + locator + " is not visible and clickable within " + timeoutSecs + " seconds. Error: " + e.getMessage());
        }
    }

	public static List<WebElement> waitForElementsToBeVisible(WebDriver driver, By locator, int timeoutSecs) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            throw new RuntimeException("Element located by " + locator + " is not visible and clickable within " + timeoutSecs + " seconds. Error: " + e.getMessage());
        }
    }
	
	public static WebElement waitForElementToBeVisible(WebDriver driver, WebElement element, int timeoutSecs) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
	        return wait.until(ExpectedConditions.visibilityOf(element));
	    } catch (Exception e) {
	        throw new RuntimeException("The specified element is not visible within " + timeoutSecs + " seconds. Error: " + e.getMessage());
	    }
	}
	
	public static void waitForElementsToBeVisible(WebDriver driver, List<WebElement> elements, int timeoutSecs) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
	        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	    } catch (Exception e) {
	        throw new RuntimeException("The specified elements are not visible within " + timeoutSecs + " seconds. Error: " + e.getMessage());
	    }
	}
	
	public static void typeTextSequentially(WebDriver driver, By locator, String text, int timeoutSecs) {
        try {
        	 WebElement element;

 	        if (timeoutSecs > 0) {
 	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
 	            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
 	        } else {
 	            element = driver.findElement(locator);
 	        }

            element.clear();

            // Type each character with a delay
            for (char currentChar : text.toCharArray()) {
                element.sendKeys(String.valueOf(currentChar)); // Type one character
                Thread.sleep(Timeouts.DELAY); // Wait for the specified delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted during typing simulation: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to type text sequentially: " + e.getMessage());
        }
    }
	
	public static String getText(WebDriver driver, WebElement element, int timeoutSecs) {
	    String actualText = null;
	    try {
	        WebElement visibleElement;

	        // Wait for the element to be present or visible
	        if (timeoutSecs > 0) {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
	            visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
	        } else {
	            visibleElement = element;
	        }

	        // Retrieve and trim the text
	        actualText = visibleElement.getText().trim();
	    } catch (Exception e) {
	        logger.error("Error retrieving text: " + e.getMessage());
	    }

	    return actualText;
	}
	
	public static String getText(WebDriver driver, WebElement parentElement, By childLocator, int timeoutSecs) {
	    String actualText = null;
	    try {
	        WebElement element;

	        // Wait for the element to be present or visible
	        if (timeoutSecs > 0) {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
	            element = wait.until(ExpectedConditions.visibilityOf(parentElement.findElement(childLocator)));
	        } else {
	        	element = parentElement.findElement(childLocator);
	        }

	        // Retrieve and trim the text
	        actualText = element.getText().trim();
	    } catch (Exception e) {
	        logger.error("Error retrieving text: " + e.getMessage());
	    }

	    return actualText;
	}
	
	public static boolean waitForTextAndVerify(WebDriver driver, WebElement parentElement, By childLocator, String expectedText, int timeoutSecs) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));

            // Wait for the child element to be visible
            WebElement childElement = wait.until(ExpectedConditions.visibilityOf(parentElement.findElement(childLocator)));

            // Get the text of the child element
            String actualText = childElement.getText().trim();
            System.out.println("actualText : " + actualText);

            // Check if the text contains the expected substring
            return actualText.contains(expectedText);
        } catch (Exception e) {
            logger.error("Error waiting for text and verifying: " + e.getMessage());
            return false;
        }
    }
	
	public static boolean waitForTextUntilMatch(WebDriver driver, By parentsLocator, By childLocator, String expectedText, int timeoutSecs, int position) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));

	        List<WebElement> centers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(parentsLocator));

	        // Ensure there are elements in the list
	        if (centers.isEmpty()) {
	            throw new RuntimeException("No centers found.");
	        }

	        WebElement parentElement = centers.get(position-1);

	        // Retry checking the text until it matches or timeout occurs
	        long startTime = System.currentTimeMillis();
	        while (System.currentTimeMillis() - startTime < timeoutSecs * 1000) {
	            WebElement childElement = wait.until(ExpectedConditions.visibilityOf(parentElement.findElement(childLocator)));

	            String actualText = childElement.getText().trim();
	   
	            if (actualText.contains(expectedText)) {
	                return true;
	            }
	        }

	        return false; 
	    } catch (Exception e) {
	        throw new RuntimeException("Error waiting for the nth center's address's text to match: " + e.getMessage());
	    }
	}
	
	public boolean verifyURL(WebDriver driver, int timeoutSecs) {
	 try {	
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
         return wait.until(ExpectedConditions.urlContains(TestInputs.FIND_CENTER_ENDPOINT));
     } catch (Exception e) {
         return false;
     }
	}

}