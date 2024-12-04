package com.mgs.Pages.WebPages;

import com.mgs.CommonUtils.CommonSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class CareerPage extends CommonSelenium {
    WebDriver driver;
    public CareerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    String btnXpath = "//button[text()='btnName']";

    public void selectCareerOption(String btn, String type)
    {
        waitFor(2);
        String careerOptionXpath = "//*[@class='productsList']//button//p[1]";

        log("Hovering over the [" + btn + "] Button");
        moveToElement(By.xpath(btnXpath.replace("btnName", btn)));

        List<WebElement> careerOptions = driver.findElements(By.xpath(careerOptionXpath));
        boolean isOptionFound = false;

        for (WebElement ele : careerOptions) {
            if (ele.getText().equals(type)) {
                waitForElementToBeClickable(driver, ele, 5);
                click(ele);
                log("Clicking the [" + type + "] Button");
                isOptionFound = true;
                break;
            }
        }
        if (!isOptionFound) {
            System.out.println("Failed to find and click the career option: " + type);
        }
    }

    public void JobApplication(String job)
    {
        List<WebElement> jobTitles = driver.findElements(By.xpath("//div[@class='title']//div[@class='job-role']"));
        for(WebElement ele : jobTitles)
        {
            if(ele.getText().equals(job))
            {
              click(ele);

            }
        }
    }


    public List<String> getJobList()
    {
       List<String> jobList = new ArrayList<>();
       List<WebElement> jobTitles = driver.findElements(By.xpath("//div[@class='title']//div[@class='job-role']"));
         for(WebElement ele : jobTitles)
         {
           jobList.add(ele.getText());
         }
         return jobList;
    }




}
