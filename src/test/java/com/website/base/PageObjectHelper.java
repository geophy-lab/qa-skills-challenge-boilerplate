package com.website.base;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;

public class PageObjectHelper {

    private static final Logger logger = LoggerFactory.getLogger(PageObjectHelper.class);

    private PageObjectHelper() {
    }

    /**
     * A wrapper for Selenide method open with additional logging for URL
     *
     * @param address URL
     */
    public static void exOpen(String address) {
        String methodName = new Throwable().getStackTrace()[TstHelper.getStackTraceIndexOne()].getMethodName();
        logger.debug("{}() - URL to open: {}", methodName, address);
        Selenide.open(address);
    }

    /**
     * A wrapper for Selenide method $ with additional logging for a selector and a located element
     *
     * @param seleniumSelector any Selenium selector like By.id(), By.name() etc.
     * @return SelenideElement
     */
    public static SelenideElement ex$(By seleniumSelector) {
        String methodName = new Throwable().getStackTrace()[TstHelper.getStackTraceIndexOne()].getMethodName();
        logger.debug("{}() - Selector: {}", methodName, seleniumSelector);
        return $(seleniumSelector);
    }

    /**
     * A wrapper for Selenide method $$ with additional logging for a selector and a located elements collection
     *
     * @param seleniumSelector any Selenium selector like By.id(), By.name() etc.
     * @return element collection or an empty list if the element was not found
     */
    public static ElementsCollection ex$$(By seleniumSelector) {
        String methodName = new Throwable().getStackTrace()[TstHelper.getStackTraceIndexOne()].getMethodName();
        logger.debug("{}() - Selector: {}", methodName, seleniumSelector);
        return $$(seleniumSelector);
    }

}
