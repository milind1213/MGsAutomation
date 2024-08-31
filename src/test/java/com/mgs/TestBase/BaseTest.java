package com.mgs.TestBase;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

import com.mgs.DriverUtils.WebBrowser;

public class BaseTest extends WebBrowser {
	protected WebDriver driver;


	public WebDriver getWeDriver() {
		String browserType = "chrome";
		boolean isHeadless = false;
		try {
			System.out.println("Launching the " + (isHeadless ? "Headless " : "") + browserType + " browser");
			getSeleniumDriver(browserType, isHeadless);
			driver = webDriver.get();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(120));
		} catch (Exception e) {
			System.err.println("Failed to launch the Browser: " + e.getMessage());
		}
		return driver;
	}
	

	@AfterClass
	public void tearDown() {
		WebDriver driver = webDriver.get();
		if (driver != null) {
			driver.quit();
		}
	}
}
