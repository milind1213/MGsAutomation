package com.mgs.Pages;

import org.openqa.selenium.WebDriver;

import com.mgs.CommonUtils.CommonSelenium;

public class WebDashboard extends CommonSelenium {
    WebDriver driver;
    public final Loginpage loginpage;
    public final Connection connection;
    public final HomePage homepage;

    public WebDashboard(WebDriver driver) {
        super(driver);
        this.driver = driver;
        loginpage = new Loginpage(driver);
        connection = new Connection(driver);
        homepage = new HomePage(driver);
    }

    public Loginpage getHomePage() {
        return loginpage;
    }

	public Connection getConnections() {
		return connection;
	}

    public HomePage getHome(){
        return homepage;
    }


}
