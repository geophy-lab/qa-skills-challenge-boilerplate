package com.website.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static com.website.base.TstHelper.getPropertyIntWithAssert;
import static com.website.base.TstHelper.getPropertyStringWithAssert;


public class TstBase {

    private static final int selenideConfigurationTimeout = getPropertyIntWithAssert("selenide.configuration.timeout");
    private static final int selenideConfigurationTimeoutRemote = getPropertyIntWithAssert("selenide.configuration.timeout.remote");
    private static final String defaultBrowserWidth = getPropertyStringWithAssert("browser.default.width");
    private static final String defaultBrowserHeight = getPropertyStringWithAssert("browser.default.height");
    private static final int browserWidth = Integer.parseInt(System.getProperty("browser.width", defaultBrowserWidth));
    private static final int browserHeight = Integer.parseInt(System.getProperty("browser.height", defaultBrowserHeight));
    private static final String stepDefinitionFileNameSuffix = getPropertyStringWithAssert("cucumber.stepDefinition.fileName.suffix");
    private static final String mdcKeyTestInfo = getPropertyStringWithAssert("mdc.key.testInfo");
    private static final Logger logger = LoggerFactory.getLogger(TstBase.class);
    private static WebDriver driver;

    @Rule
    public TestName testName = new TestName();
    @Rule
    public ScreenShotRule screenShootRule = new ScreenShotRule(testName);

    public TstBase() {
        final String stepDefsSuffix = stepDefinitionFileNameSuffix;
        String classSimpleName = getClass().getSimpleName();
        if (!classSimpleName.endsWith(stepDefsSuffix)) {
            MDC.put(mdcKeyTestInfo, classSimpleName);
        } else {
            MDC.put(mdcKeyTestInfo, stepDefsSuffix);
        }
    }

    @BeforeClass
    public static void setUp() {
        MDC.put(mdcKeyTestInfo, "setUp");
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getDriver();
        if (driver == null) {
            logger.error("WebDriver has not been initialized");
            return;
        }

        driver.manage().window().setSize(new Dimension(browserWidth, browserHeight));
        WebDriverRunner.setWebDriver(driver);
        Configuration.fastSetValue = true;
        Configuration.timeout = selenideConfigurationTimeout;
        Configuration.pageLoadStrategy = "eager";
        Configuration.savePageSource = false;
        Configuration.reportsFolder = "target/screenshots";

        if (webDriverFactory.useRemoteWebDriver)
            Configuration.timeout = selenideConfigurationTimeoutRemote;

        logger.debug("Selenide Configuration.timeout: {}", Configuration.timeout);
    }

    @AfterClass
    public static void tearDown() {
        System.out.println(); // a workaround to separate Cucumber logs from JUnit logs in the console output
        MDC.put(mdcKeyTestInfo, "tearDown");
        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    public void setupLogContext() {
        MDC.put(mdcKeyTestInfo, testName.getMethodName());
    }
}
