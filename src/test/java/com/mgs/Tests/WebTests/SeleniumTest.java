package com.mgs.Tests.WebTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumTest {
public static void main (String [] args ){
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("ignore-certificate-errors");
    options.addArguments("--ignore-ssl-errors=yes");

    WebDriver driver = new ChromeDriver(options);

    driver.get("https://www.uitestingplayground.com/dynamictable");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Internet Explorer']//following-sibling::span[3]")));
    String a =  ele.getText();
    System.out.println(a);
}
}
