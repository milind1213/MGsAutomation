package com.mgs.Tests.Rough;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGrid {
    public static void main (String [] args){
        String hubURL = "http://localhost:4444"; // replace <hub_ip> and <hub_port> with your Grid Hub details
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome"); // choose browser, e.g., "chrome", "firefox"
        capabilities.setPlatform(Platform.ANY); // or specify a platform, e.g., Platform.WINDOWS

        WebDriver driver = null;
        try {
            // Connect to Selenium Grid with desired capabilities
            driver = new RemoteWebDriver(new URL(hubURL), capabilities);

            // Run a test - navigate to a website
            driver.get("https://www.example.com");
            System.out.println("Page title is: " + driver.getTitle());

            // Example assertion or condition
            if (driver.getTitle().contains("Example Domain")) {
                System.out.println("Test Passed!");
            } else {
                System.out.println("Test Failed!");
            }

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } finally {
            // Close the browser
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
