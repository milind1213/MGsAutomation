package com.mgs.Pages;

import com.mgs.CommonUtils.CommonSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends CommonSelenium {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    By connectionsList = By.xpath("//p[@class='listHeading']");
    By sortOptions = By.xpath("//li[@class='dropdown-menu__item__2cQ8']");
    By sortResults = By.xpath("//a[@class='user-name']//h3");


    public WebElement clickOnConnect() {
        waitFor(3);
        return driver.findElement(By.xpath("//button[normalize-space()='Connect']//*[name()='svg']"));
    }

    public void selectConnectionType(String type) {
        List<WebElement> elements = driver.findElements(connectionsList);
        for (WebElement element : elements) {
            if (element.getText().equalsIgnoreCase(type)) {
                element.click();
                break;
            }
        }
    }

    public void clickSortButton() {
        click(By.xpath("//span[@class='btnLabel__aEhVM']"));
    }

    public List<WebElement> getConnectionOptions() {
        return driver.findElements(sortOptions);
    }

    public List<WebElement> getSortResults() {
        return driver.findElements(sortResults);
    }




}
