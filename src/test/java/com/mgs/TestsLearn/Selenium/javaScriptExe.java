package com.mgs.TestsLearn.Selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class javaScriptExe {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement ele = driver.findElement(By.id(""));
        // Instead of SendKeys
        js.executeScript("arguments[0].setAttribute('value','Milind')", ele);

        // Click
        js.executeScript("arguments[0].click()", ele);

        // Scroll down by pixel
        js.executeScript("window.scrollBy(0,3000)", "");
        System.out.println(js.executeScript("return window.pageYOffset;")); // Print scrolled pixel

        // Scroll Until Element Visible
        js.executeScript("arguments[0].scrollIntoView();", ele);
        System.out.println(js.executeScript("return window.pageYOffset;")); // Print scrolled pixel

        // Scroll page till End Of page
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        // Scroll page Initial Position
        js.executeScript("window.scrollBy(0,-document.body.scrollHeight)");

        // Set Zoom
        js.executeScript("document.body.style.zoom='80%'");
    }
}