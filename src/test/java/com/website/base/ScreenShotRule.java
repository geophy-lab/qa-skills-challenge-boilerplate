package com.website.base;

import com.codeborne.selenide.Selenide;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ScreenShotRule extends TestWatcher {

    private final TestName testName;

    public ScreenShotRule(TestName testName) {
        this.testName = testName;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        Selenide.screenshot("Junit-" + TstHelper.generateUniqueString(testName.getMethodName()));
    }
}
