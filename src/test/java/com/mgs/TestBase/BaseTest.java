package com.mgs.TestBase;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.mgs.CommonConstants;
import com.mgs.Utils.TestListeners;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

import com.mgs.DriverUtils.WebBrowser;
import org.testng.annotations.AfterMethod;

import static com.mgs.Utils.FileUtil.getProperty;

public class BaseTest extends WebBrowser {
	WebDriver driver;

	public WebDriver getWeDriver() {
		String browserType = getProperty(CommonConstants.MGS, CommonConstants.BROWSER);
		boolean isHeadless =  Boolean.parseBoolean(getProperty(CommonConstants.MGS, CommonConstants.RUNMODE_IS_HEADLESS));
		try {
			System.out.println("Launching the " + (isHeadless ? "Headless " : "") + browserType + " browser");
			initializeWebDriver(browserType,isHeadless);
			driver = webDriver.get();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(120));
			driver.manage().window().maximize();
		} catch (Exception e) {
			System.err.println("Failed to launch the Browser: " + e.getMessage());
		}
		return driver;
	}




















	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public void log(String message) {
		try {
			String timestamp = new SimpleDateFormat("h:mm:ss a").format(new Date());
			ExtentTest extentTest = TestListeners.extentTest.get();
			if (extentTest != null) {
				extentTest.log(Status.INFO, message);
			}
			System.out.println("[" + timestamp + "] " + "INFO: " + message);
		} catch (Exception e) {
			System.err.println("Failed to log message: " + message + " Error : " + e.getMessage());
		}
	}
}
