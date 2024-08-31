package com.mgs.Pages;

import org.openqa.selenium.WebDriver;

import com.mgs.CommonUtils.CommonSelenium;

public class WebDashboard extends CommonSelenium {
    WebDriver driver;
    public final Homepage homepage;
    public final Connection connection;

    public WebDashboard(WebDriver driver) {
        super(driver);
        this.driver = driver;
        homepage = new Homepage(driver);
        connection = new Connection(driver);
    }

    public Homepage getHomePage() {
        return homepage;
    }

	public Connection getConnections() {
		return connection;
	}


}
