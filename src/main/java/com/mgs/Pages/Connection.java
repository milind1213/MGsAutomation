package com.mgs.Pages;

import com.mgs.CommonUtils.CommonSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Connection extends CommonSelenium {
    WebDriver driver;
    public Connection(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

   public WebElement clickOnConnect() {
      waitFor(3);
      return driver.findElement(By.xpath("//button[.='Connect']"));
   }




}
