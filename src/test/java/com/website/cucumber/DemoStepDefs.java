package com.website.cucumber;

import com.website.base.TstBase;
import com.website.pages.DemoPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.website.base.TstHelper.getPropertyStringWithAssert;

public class DemoStepDefs extends TstBase {

    private static final Logger logger = LoggerFactory.getLogger(DemoStepDefs.class);
    private final String sutUrl = getPropertyStringWithAssert("sut.url");
    private final DemoPage demoPage = new DemoPage();

    @When("The user goes to the page {string}")
    public void theUserGoesToThePage(String page) {
        String urlToOpen = sutUrl + page;
        logger.debug("{}", urlToOpen);
        demoPage.openWebPageByItsURL(urlToOpen);
    }

    @Then("The user should see the menu item {string}")
    public void theUserShouldSeeTheMenuItem(String tabText) {
        demoPage.getDemoPageMenuItemByText(tabText).shouldBe(visible);
    }

}
