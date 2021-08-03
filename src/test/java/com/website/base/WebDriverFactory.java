package com.website.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

class WebDriverFactory {
    private static final WebDriverType defaultDriverType = WebDriverType.CHROME;
    public final boolean useRemoteWebDriver = Boolean.getBoolean("use.selenium.grid");
    private final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private final String browser = System.getProperty("browser", defaultDriverType.toString()).toUpperCase();
    private WebDriver webdriver;
    private WebDriverType selectedWebDriverType;

    WebDriver getDriver() {
        if (null == webdriver) {
            determineEffectiveDriverType();
            final MutableCapabilities browserOptions = selectedWebDriverType.getBrowserOptions();
            instantiateWebDriver(browserOptions);
        }
        return webdriver;
    }

    private void determineEffectiveDriverType() {
        WebDriverType driverType = defaultDriverType;
        try {
            driverType = WebDriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            logger.error("Unknown driver specified, defaulting to '{}'", driverType);
        } catch (NullPointerException ignored) {
            logger.error("No driver specified, defaulting to '{}", driverType);
        }
        selectedWebDriverType = driverType;
    }

    private void instantiateWebDriver(final MutableCapabilities browserOptions) {
        if (useRemoteWebDriver) {
            try {
                final String seleniumGridUrlString = System.getProperty("selenium.grid.url");
                if (StringUtils.isBlank(seleniumGridUrlString)) {
                    throw new IllegalArgumentException("'selenium.grid.url' is null or blank!");
                }

                final URL seleniumGridURL = new URL(seleniumGridUrlString);
                final URL seleniumGridConsoleURL = new URL(seleniumGridUrlString.replace("wd/hub", "grid/console"));
                final String desiredBrowserVersion = System.getProperty("desired.browser.version");
                final String desiredPlatform = System.getProperty("desired.platform");

                if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                    browserOptions.setCapability("platform", Platform.valueOf(desiredPlatform.toUpperCase()));
                }

                if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                    browserOptions.setCapability("version", desiredBrowserVersion);
                }

                webdriver = new RemoteWebDriver(seleniumGridURL, browserOptions);
                //Files will be uploaded from local machine via the selenium grid.
                ((RemoteWebDriver) webdriver).setFileDetector(new LocalFileDetector());

                logger.debug("Grid URL: {}", seleniumGridURL);
                logger.debug("Grid Console: {}", seleniumGridConsoleURL);
                logger.debug("Remote Driver: {}", webdriver);
            } catch (MalformedURLException e) {
                logger.error("Grid URL is malformed");
            }
        } else {
            logger.debug("Using Local Web Driver");
            webdriver = selectedWebDriverType.getLocalWebDriver();
        }
    }
}
