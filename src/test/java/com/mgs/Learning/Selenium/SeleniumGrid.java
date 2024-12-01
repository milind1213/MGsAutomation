package com.mgs.Learning.Selenium;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGrid {
    public static void main (String [] args){
        String hubURL = "http://localhost:4444";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.ANY);

        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(hubURL), capabilities);
            driver.get("https://www.example.com");
            System.out.println("Page title is: " + driver.getTitle());
            if (driver.getTitle().contains("Example Domain")) {
                System.out.println("Test Passed!");
            } else {
                System.out.println("Test Failed!");
            }

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
