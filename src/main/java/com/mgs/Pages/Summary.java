package com.mgs.Pages;

import org.openqa.selenium.WebDriver;
import com.mgs.CommonUtils.CommonSelenium;

public class Summary extends CommonSelenium{
   WebDriver Driver;
    public Summary(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
}
