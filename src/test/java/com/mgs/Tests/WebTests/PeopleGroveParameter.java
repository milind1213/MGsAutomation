package com.mgs.Tests.WebTests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PeopleGroveParameter {
    WebDriver driver;

    @Parameters("Browser")
    @Test
    public void paraterTest(String browserName) throws InterruptedException {
        System.out.println("Parater Values is " + browserName);
        if (browserName.contains("chrome")) {
            WebDriverManager.chromedriver().setup();
                 driver = new ChromeDriver();
        } else if (browserName.contains("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new ChromeDriver();
        }
        driver.get("https://basecopy5.staging.pg-test.com/v2/");
        driver.manage().window().maximize();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[.='Sign In']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("mgs@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Mgs@1213");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.close();
    }
}
