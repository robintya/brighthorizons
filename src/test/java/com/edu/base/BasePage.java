package com.edu.base;

import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Common methods can be added here
    public String getPageTitle() {
        return driver.getTitle();
    }
}
