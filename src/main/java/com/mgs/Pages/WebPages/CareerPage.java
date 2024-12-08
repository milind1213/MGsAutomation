package com.mgs.Pages.WebPages;

import com.mgs.CommonUtils.CommonSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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

        List<WebElement> careerOptions = getElements(By.xpath(careerOptionXpath));
        boolean isOptionFound = false;

        for (WebElement ele : careerOptions) {
            if (ele.getText().equals(type)) {
                waitForElementToBeClickable(ele, 5);
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
        String xpath ="//div[@class='title']//div[@class='job-role']";
        List<WebElement> jobTitles = getElements(By.xpath(xpath));
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
       List<WebElement> jobTitles = getElements(By.xpath("//div[@class='title']//div[@class='job-role']"));
         for(WebElement ele : jobTitles)
         {
           jobList.add(ele.getText());
         }
         return jobList;
    }

    public void selectProfileColumn(String colName)
    {
       try {
           String xpath = "(//*[@role='tablist'])[2]//button[text()='" + colName + "']";
           WebElement colButton = driver.findElement(By.xpath(xpath));
           waitForElementToBeClickable(colButton, 5);  // Wait for element to be clickable
           click(colButton);
       }catch (NoSuchElementException e)
       {
           e.printStackTrace();
       }
    }

    public void clickOnApplyButton()
    {
        By applyNowButton = By.xpath("//span[text()='Apply Now']");
        try {
            if (!isElementDisplayed(applyNowButton))
            {
                waitForElementToBeClickable(applyNowButton, 5);
                ClickWithJS(applyNowButton);
            } else {
                ClickWithJS(applyNowButton);
            }
        } catch (Exception e)
        {
            Assert.fail("Failed to click on [Apply Now] button");
        }
    }

    public void clickUploadResumeBtn(String filePath)
    {
        String xpath  = "//button[normalize-space()='Upload 1 file']";
        try {
            log("clicking on the [Upload resume] button");
            click(By.xpath(btnXpath.replace("btnName", "Upload resume")));

            log("clicking on the [browse files] button");
            WebElement fileInput = driver.findElement(By.xpath("//button[normalize-space()='browse files']"));
            click(fileInput);
            uploadFileViaRobot(filePath);
            waitForElementDisplay(By.xpath(xpath),5);

            log("clicking on the [Upload 1 file] button");
            click(By.xpath(xpath));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("File upload failed: " + e.getMessage());
        }
    }

    public String getUploadSuccessMessage(){
        String xpath = "//div[contains(text(),'Resume uploaded successfully')]";
        waitForElementDisplay(By.xpath(xpath),10);
        return getText(By.xpath(xpath));
    }

    public void clickJobPostBtn(){
        click(By.xpath("//a[text()='Post a Job']"));
    }

    public void clickShareOrHireBtn(String text){
        clickJSOnTextSpan(text);
    }

     public void selectCompanyName(String orgName,String expOrganization)
     {
        String xpathList = "//*[@class='Autocomplete-module_optionLabelOverflow__jtk8K']";
        By searchLocator = By.xpath("//*[@placeholder='Select or Search for a Company']");
        waitForElementDisplay(searchLocator,5);
        sendKeys(searchLocator,orgName);
        waitFor(1);
        List<WebElement> drpList = getElements(By.xpath(xpathList));
        for(WebElement e : drpList)
         {
             if(e.getText().contains(expOrganization)){
                 click(e);
             }
         }
     }

     public void typeRole(String text)
     {
        sendKeys(By.xpath("//input[@id='jobTitle']"),text);
     }

     public void searchSelectLocation(String city)
     {
        String sugXpath = "//*[@class='Autocomplete-module_optionLabelOverflow__jtk8K']";
        sendKeys(By.id("location"),city);
        waitForElementToBeClickable(By.xpath(sugXpath),5);
        List<WebElement> sugsList = getElements(By.xpath(sugXpath));
        for(WebElement e : sugsList){
            e.click();
            break;
        }
     }

     public void writeDescription(String descripions,String note)
     {
        sendKeys(By.id("jobDescription"),descripions);
        sendKeys(By.id("note"),note);
     }
}
