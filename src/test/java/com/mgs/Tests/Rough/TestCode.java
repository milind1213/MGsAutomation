package com.mgs.Tests.Rough;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
public class TestCode {
    public  static void main(String [] args) throws IOException, AWTException {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-infobars");

            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", 375);
            deviceMetrics.put("height", 812);
            deviceMetrics.put("pixelRatio", 3.0);

            // Set the device name or use custom metrics
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);

            WebDriver driver = new ChromeDriver(options);
           /* Dimension dimension = new Dimension(375, 700);
            driver.manage().window().setSize(dimension);*/
            driver.get("https://www.nobroker.in/");

    }



























}
