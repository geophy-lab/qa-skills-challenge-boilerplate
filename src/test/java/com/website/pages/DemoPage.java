package com.website.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.website.base.PageObjectHelper.ex$;
import static com.website.base.PageObjectHelper.exOpen;

public class DemoPage {

    public void openWebPageByItsURL(String address) {
        exOpen(address);
    }

    public SelenideElement getDemoPageMenuItemByText(String tabText) {
        return ex$(byLinkText(tabText));
    }

}
