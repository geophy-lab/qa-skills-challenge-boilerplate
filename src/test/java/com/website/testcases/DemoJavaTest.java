package com.website.testcases;

import com.website.base.TstBase;
import com.website.cucumber.DemoStepDefs;
import com.website.pages.DemoPage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.website.base.TstHelper.getPropertyStringWithAssert;

public class DemoJavaTest extends TstBase {

    private static final Logger logger = LoggerFactory.getLogger(DemoStepDefs.class);
    private final String sutUrl = getPropertyStringWithAssert("sut.url");
    private final DemoPage demoPage = new DemoPage();

    @Test
    public void demoJavaTest() {
        logger.debug("{}", sutUrl);
        demoPage.openWebPageByItsURL(sutUrl);
        demoPage.getDemoPageMenuItemByText("Read").shouldBe(visible);
        demoPage.getDemoPageMenuItemByText("View source").shouldBe(visible);
    }
}
