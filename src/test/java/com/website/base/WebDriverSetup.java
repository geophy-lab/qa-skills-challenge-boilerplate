package com.website.base;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public interface WebDriverSetup {

    MutableCapabilities getBrowserOptions();

    WebDriver getLocalWebDriver();

}
