package com.mgs.TestBase;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.mgs.CommonConstants;
import com.mgs.Utils.Reporting.TestListeners;
import org.openqa.selenium.WebDriver;

import com.mgs.DriverUtils.WebBrowser;
import org.testng.annotations.AfterMethod;

import static com.mgs.Utils.FileUtil.getProperty;
import static com.mgs.Utils.Reporting.TestListeners.extentTest;

public class BaseTest extends WebBrowser {
    WebDriver driver;

    public WebDriver getWebDriver() {
        String env = getProperty(CommonConstants.MGS, CommonConstants.EXECUTION_ENV);
        String browserType = getProperty(CommonConstants.MGS, CommonConstants.BROWSER);
        boolean isHeadless = Boolean.parseBoolean(getProperty(CommonConstants.MGS, CommonConstants.RUNMODE_IS_HEADLESS));
        try {
            if (env.equalsIgnoreCase(CommonConstants.LOCAL)) {
                initializeWebDriver(browserType, isHeadless);
                System.out.println("Launching the Local " + (isHeadless ? "Headless " : "") + browserType + " browser");
            } else if (env.equalsIgnoreCase(CommonConstants.REMOTE)) {
                initializeRemoteWebDriver(browserType);
                System.out.println("Launching the Remote " + (isHeadless ? "Headless " : "") + browserType + " browser");
            }
            driver = webDriver.get();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
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
            if (extentTest.get() != null) {
                extentTest.get().log(Status.INFO, message);
            } else {
                System.err.println("ExtentTest is null for log message: " + message);
            }
            System.out.println("[" + timestamp + "] INFO: " + message);
        } catch (Exception e) {
            System.err.println("Failed to log message: " + message + " Error: " + e.getMessage());
        }
    }
}
  /*  public static String getEnvironmentUrl() {
        if (System.getProperty(CommonConstants.IS_EXECUTION_PLATFORM_JENKINS).equalsIgnoreCase("true")) {
            if (System.getProperty("Environment").trim().equalsIgnoreCase("stage")) {
                return System.getProperty(CommonConstants.STAGE_LOGIN);
            } else if (System.getProperty("Environment").trim().equalsIgnoreCase("preprod")) {
                return System.getProperty(CommonConstants.PREPROD_LOGIN);
            } else {
                return System.getProperty(CommonConstants.PREPROD_LOGIN).replace("bb-pre-prod", System.getProperty("Environment").trim());
            }
        } else {
            if (System.getProperty(CommonConstants.LOCAL_EXECUTION_PLATFORM).trim().equalsIgnoreCase("stage")) {
                return System.getProperty(CommonConstants.STAGE_LOGIN);
            } else if (System.getProperty(CommonConstants.LOCAL_EXECUTION_PLATFORM).trim().equalsIgnoreCase("preprod")) {
                return System.getProperty(CommonConstants.PREPROD_LOGIN);
            } else {
                return System.getProperty(CommonConstants.PREPROD_LOGIN).replace("pre-prod", System.getProperty(CommonConstants.LOCAL_EXECUTION_PLATFORM).trim());
            }
        }
    }
*/