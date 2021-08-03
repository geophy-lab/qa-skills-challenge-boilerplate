package com.website.cucumber;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.website.base.TstBase;
import com.website.base.TstHelper;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScenarioHooks extends TstBase {

    @After
    public void makeScreenshotIfScenarioFailed(Scenario scenario) {
        if (scenario.isFailed()) {
            String fileName = "Cucumber-" + TstHelper.generateUniqueString(scenario.getName());
            Selenide.screenshot(fileName);
            TakesScreenshot takesScreenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver());
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", fileName);
        }
    }
}
