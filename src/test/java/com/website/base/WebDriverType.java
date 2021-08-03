package com.website.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static com.website.base.TstHelper.getPropertyStringWithAssert;

public enum WebDriverType implements WebDriverSetup {

    FIREFOX {
        @Override
        public FirefoxOptions getBrowserOptions() {
            final FirefoxOptions options = new FirefoxOptions();
            options.setCapability("marionette", Boolean.parseBoolean(System.getProperty("marionette", "true")));
            options.setHeadless(Boolean.getBoolean("browser.headless"));
            return options;
        }

        @Override
        public WebDriver getLocalWebDriver() {
            WebDriverManager.firefoxdriver().setup();
            logger.debug("Gecko wdm.chromeDriverVersion property: {}", System.getProperty("wdm.geckoDriverVersion"));
            logger.debug("Gecko Driver Version: {}", WebDriverManager.firefoxdriver().getDownloadedDriverVersion());
            FirefoxOptions options = getBrowserOptions();
            return new FirefoxDriver(options);
        }
    },
    CHROME {
        @Override
        public ChromeOptions getBrowserOptions() {
            HashMap<String, Object> chromePrefs = new HashMap<>();
            final ChromeOptions options = new ChromeOptions();
            options.setHeadless(Boolean.getBoolean("browser.headless"));
            options.setExperimentalOption("prefs", chromePrefs);
            /* A workaround to set browser window size via arguments because driver.manage().window().setSize() is
             ignored in headless mode */
            String defaultBrowserWidth = getPropertyStringWithAssert("browser.default.width");
            String defaultBrowserHeight = getPropertyStringWithAssert("browser.default.height");
            options.addArguments("window-size=" + defaultBrowserWidth + "," + defaultBrowserHeight + "");
            return options;
        }

        @Override
        public WebDriver getLocalWebDriver() {
            WebDriverManager.chromedriver().setup();
            logger.debug("Chrome wdm.chromeDriverVersion property: {}", System.getProperty("wdm.chromeDriverVersion"));
            logger.debug("Chrome Driver Version: {}", WebDriverManager.chromedriver().getDownloadedDriverVersion());
            ChromeOptions options = getBrowserOptions();
            return new ChromeDriver(options);
        }
    };

    private static final Logger logger = LoggerFactory.getLogger(WebDriverType.class);
}
