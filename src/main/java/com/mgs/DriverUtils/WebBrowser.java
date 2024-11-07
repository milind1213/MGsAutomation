package com.mgs.DriverUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import com.mgs.CommonConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.mgs.Utils.FileUtil.getProperty;

public class WebBrowser {
    WebDriver driver;
    protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static final List<WebDriver> webDriverList = Collections.synchronizedList(new ArrayList<>());

    public void initializeWebDriver(String browserType, boolean isHeadless) throws MalformedURLException
    {
        if (browserType.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup(); // Correct for Chrome
            ChromeOptions options = new ChromeOptions();
            addCommonArguments(options, isHeadless);
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup(); // Fix for Firefox (was wrongly using chromedriver)
            FirefoxOptions options = new FirefoxOptions();
            addCommonArguments(options, isHeadless);
            driver = new FirefoxDriver(options);
        } else if(browserType.equalsIgnoreCase("edge"))
        {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            addCommonArguments(options, isHeadless);
            driver = new EdgeDriver(options);
        }
        if (driver != null) {
            webDriver.set(driver);
            webDriverList.add(driver);
        }
    }

    public void initializeRemoteWebDriver(String browserType ) throws MalformedURLException
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String executionPlatform = System.getenv("EXECUTION_PLATFORM");
        if (executionPlatform == null) {
            executionPlatform = getProperty(CommonConstants.MGS, CommonConstants.EXECUTION_PLATFORM);
        }

        switch (executionPlatform.toLowerCase())
        {
            case "windows" -> capabilities.setPlatform(Platform.WINDOWS);
            case "linux" -> capabilities.setPlatform(Platform.LINUX);
            case "mac" -> capabilities.setPlatform(Platform.MAC);
            default -> throw new IllegalArgumentException("Invalid platform specified");
        }

        if (browserType.equalsIgnoreCase("chrome"))
        {
            ChromeOptions options = new ChromeOptions();
            capabilities.merge(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            capabilities.merge(options);
        }else if (browserType.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            capabilities.merge(options);
        }

        driver = new RemoteWebDriver(new URL(getProperty(CommonConstants.MGS, CommonConstants.HUB_URL)), capabilities);
        webDriver.set(driver);
        webDriverList.add(driver);
    }

    private void addCommonArguments(Object options, boolean isHeadless)
    {
        if (options instanceof ChromeOptions chromeOptions)
        {
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

        } else if (options instanceof FirefoxOptions firefoxOptions) {
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
