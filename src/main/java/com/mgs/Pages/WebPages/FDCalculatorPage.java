package com.mgs.Pages.WebPages;

import com.mgs.CommonUtils.CommonSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FDCalculatorPage extends CommonSelenium {
    WebDriver driver;

    public FDCalculatorPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    By principleField = By.xpath("//input[@id='principal']");
    By interestField = By.xpath("//input[@id='interest']");
    By tenure = By.xpath("//input[@id='tenure']");
    By tenureDrp = By.xpath("//select[@id='tenurePeriod']");
    By frequncyDrp = By.xpath("//select[@id='frequency']");
    By calculateBtn = By.xpath("//img[contains(@src,'btn_calcutate.gif')]");
    By maturityAmt = By.xpath("//span[@id='resp_matval']//strong");
    By clearBtn = By.xpath("//img[@class='PL5']");

    public void calculateRateOfInterest(String principle, String roi, String pr1, String pr2, String freq) {
        try {
            log("Entering Principle Amount : " + principle);
            sendKeys(principleField, principle);

            log("Entering Rate of Interest : " + roi);
            sendKeys(interestField, roi);

            log("Entering Period : " + roi);
            sendKeys(tenure, pr1);

            log("Selecting Dropdown Option : " + pr2);
            selectDropdownOptionByText(driver, tenureDrp, pr2);

            log("Selecting Frequency Dropdown Option : " + freq);
            selectDropdownOptionByText(driver, frequncyDrp, freq);

            log("Clicking on Calculate");
            javascriptClick(calculateBtn);

        } catch (Exception e) {
            log("An error occurred while calculating ROI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getMaturity() {
        try {
            log("Retrieve the maturity amount.");
            return getText(maturityAmt);
        } catch (Exception e) {
            log("Failed to retrieve the maturity amount: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void clickOnClearBtn() {
        waitFor(3);
        try {
            log("Clicking on Clear button");
            javascriptClick(clearBtn);
        } catch (Exception e) {
            log("Failed to click the Clear button: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
