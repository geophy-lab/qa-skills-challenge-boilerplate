package com.website.cucumbertestrunners;

import com.website.base.TstBase;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"json:target/cucumber-report/DemoCucumber.json", "pretty"},
    features = {"src/test/resources/com/website/cucumber/DemoCucumber.feature"},
    glue = {"com.website.cucumber"}
)
public class DemoCucumberRunnerTest extends TstBase {
}
