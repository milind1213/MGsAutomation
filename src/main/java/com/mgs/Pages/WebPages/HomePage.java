package com.mgs.Pages.WebPages;

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
    By sortByChar = By.xpath("//div[contains(text(),'Name (A-Z)')]");
    By groupNames = By.xpath("//h2//span[@role='link']");
    By connectBtn = By.xpath("//button[normalize-space()='Connect']//*[name()='svg']");

    public void moveOnConnect()
    {
        waitForElementToBeClickable(connectBtn,10);
        moveToElement(connectBtn);
    }

    public void selectConnectionType(String type)
    {
        List<WebElement> elements = driver.findElements(connectionsList);
        for (WebElement element : elements)
        {
            if (element.getText().equalsIgnoreCase(type))
            {
                element.click();
                break;
            }
        }
    }

    public void clickSortButton()
    {
        click(By.xpath("//span[@class='btnLabel__aEhVM']"));
    }

    public void clickedOnA2ZDropdowns()
    {

        click(sortByChar);
    }

    public List<WebElement> getConnectionOptions()
    {
        return driver.findElements(sortOptions);
    }

    public List<WebElement> getSortResults()
    {
        waitForElementClickable(driver.findElement(sortResults), 5);
        return driver.findElements(sortResults);
    }

    public List<WebElement> getGroupNames()
    {
        return driver.findElements(groupNames);
    }

    public int getMembersCounts() {
        int sum = 0;
        List<WebElement> elements = driver.findElements(By.xpath("//span[@class='group-members']"));
        for (WebElement element : elements) {
            String[] memberParts = element.getText().trim().split(":");
            if (memberParts.length == 2) { // Ensure there are exactly 2 parts
                String numStr = memberParts[1].trim(); // Get the numeric part and trim it
                try {
                    int num = Integer.parseInt(numStr); // Parse the number
                    sum += num;
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing number: " + numStr);
                }
            } else {
                System.err.println("Unexpected format: " + element.getText());
            }
        }
        return sum;
    }

    public int getPrograms()
    {
        String programs = getText(By.xpath("(//span[contains(.,'Programs')])[3]"));
        String programCount = programs.replaceAll("[A-Za-z]", "").trim();
        return Integer.parseInt(programCount);
    }


}
