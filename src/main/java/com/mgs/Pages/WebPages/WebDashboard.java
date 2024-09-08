package com.mgs.Pages.WebPages;

import org.openqa.selenium.WebDriver;

import com.mgs.CommonUtils.CommonSelenium;

public class WebDashboard extends CommonSelenium {
    WebDriver driver;
    public final Loginpage loginpage;
    public final HomePage homepage;
    public final Summary summary;

    public WebDashboard(WebDriver driver) {
        super(driver);
        this.driver = driver;
        loginpage = new Loginpage(driver);
        homepage = new HomePage(driver);
        summary = new Summary(driver);
    }

    public Loginpage getHomePage() {
        return loginpage;
    }

    public HomePage getHome() {
        return homepage;
    }

    public Summary getSummaryPage() {
        return summary;
    }


}
