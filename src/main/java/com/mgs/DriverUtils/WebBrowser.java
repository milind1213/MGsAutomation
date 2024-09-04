
package com.mgs.DriverUtils;

import java.util.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class WebBrowser {
	WebDriver driver;
	protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
	private static final List<WebDriver> webDriverList = Collections.synchronizedList(new ArrayList<>());

	public void initializeWebDriver(String browserType, boolean isHeadless) {

		if (browserType.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
		 	ChromeOptions options = new ChromeOptions();
			addCommonArguments(options, isHeadless);
			driver = new ChromeDriver(options);
		} else if (browserType.equalsIgnoreCase("firefox")) {
			WebDriverManager.chromedriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			addCommonArguments(options, isHeadless);
			driver = new FirefoxDriver(options);
		}
		if (driver != null) {
			webDriver.set(driver);
			webDriverList.add(driver); 
		}
	}

	private void addCommonArguments(Object options, boolean isHeadless) {
		if (options instanceof ChromeOptions) {
			ChromeOptions chromeOptions = (ChromeOptions) options;
			chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			if (isHeadless) {
				chromeOptions.addArguments("--headless", "--window-size=1920,1080");
			} 
			chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-extensions",
					"--dns-prefetch-disable", "--disable-gpu", "--start-maximized", "--disable-web-security",
					"--no-proxy-server");
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			chromeOptions.setExperimentalOption("prefs", prefs);
			
		} else if (options instanceof FirefoxOptions) {
			FirefoxOptions firefoxOptions = (FirefoxOptions) options;
			firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true); 
			if (isHeadless) {
				firefoxOptions.addArguments("--headless", "--window-size=1920,1080");
			}
			firefoxOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-extensions",
					"--dns-prefetch-disable", "--disable-gpu", "--start-maximized", "--disable-web-security",
					"--no-proxy-server");
			firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
		}
	}

}
