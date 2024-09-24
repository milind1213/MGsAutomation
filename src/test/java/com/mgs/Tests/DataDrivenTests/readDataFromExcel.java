package com.mgs.Tests.DataDrivenTests;

import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.FDCalculatorPage;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.TestListeners;
import com.mgs.Utils.UtilsExcel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class readDataFromExcel extends BaseTest {
    WebDriver driver;
    String filePath = System.getProperty("user.dir") + "/TestData/FixedDeposits.xlsx";
    FDCalculatorPage user;

    @BeforeTest
    public void launchApp() {
        driver = getWebDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_FD_URL));
        user = new FDCalculatorPage(driver);
    }

    @Test(priority = 1)
    public void calculateFdRatesWithSimple() throws IOException {
        int rows = UtilsExcel.getRowCount(filePath, "Sheet1");
        for (int i = 1; i <= rows; i++) {
            String principle = UtilsExcel.getCellData(filePath, "Sheet1", i, 0);
            String roi = UtilsExcel.getCellData(filePath, "Sheet1", i, 1);
            String per1 = UtilsExcel.getCellData(filePath, "Sheet1", i, 2);
            String per2 = UtilsExcel.getCellData(filePath, "Sheet1", i, 3);
            String frequncy = UtilsExcel.getCellData(filePath, "Sheet1", i, 4);
            String exp_maturityValue = UtilsExcel.getCellData(filePath, "Sheet1", i, 5);

            log("Performing FD calculation for row: " + i);
            user.calculateRateOfInterest(principle, roi, per1, per2, frequncy);

            String actualMaturityAmt = user.getMaturity();
            log("Expected Maturity: " + exp_maturityValue + ", Actual Maturity: " + actualMaturityAmt);

            if (Double.parseDouble(exp_maturityValue) == Double.parseDouble(actualMaturityAmt)) {
                log("Test passed for row: " + i);
                UtilsExcel.setCellData(filePath, "Sheet1", i, 7, "Passed");
                UtilsExcel.fillGreenColor(filePath, "Sheet1", i, 7);
            } else {
                log("Test failed for row: " + i);
                UtilsExcel.setCellData(filePath, "Sheet1", i, 7, "Failed");
                UtilsExcel.fillRedColor(filePath, "Sheet1", i, 7);
            }
            user.clickOnClearBtn();
        }
    }

}
