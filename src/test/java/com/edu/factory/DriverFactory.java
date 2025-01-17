package com.edu.factory;

import com.edu.config.BrowserType;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Sets up the WebDriver instance based on the browser type.
    public static void setDriver(BrowserType browserType) {
        if (driverThreadLocal.get() == null) {
            switch (browserType) {
                case CHROME:
                    driverThreadLocal.set(initializeChromeDriver());
                    break;
                case FIREFOX:
                    driverThreadLocal.set(new FirefoxDriver());
                    break;
                case EDGE:
                    driverThreadLocal.set(new EdgeDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserType);
            }
        }
    }

    // Gets the WebDriver instance for the current thread.
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    // Quits the WebDriver instance for the current thread.
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }

    // Initializes the ChromeDriver with custom options.
    private static WebDriver initializeChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");

        // Set preferences to disable location prompts
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2); // Block location access
        chromeOptions.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(chromeOptions);
    }
}
