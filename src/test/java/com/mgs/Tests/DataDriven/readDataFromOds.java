package com.mgs.Tests.DataDriven;

import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.FDCalculatorPage;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.UtilsOds;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.mgs.Utils.FileUtil.getProperty;

public class readDataFromOds extends BaseTest {
    WebDriver driver;
    String filePath = System.getProperty("user.dir") + "/TestData/DepositDetails.ods";
    FDCalculatorPage user;

    @BeforeTest
    public void launchApp() {
        driver = getWebDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_FD_URL));
        user = new FDCalculatorPage(driver);
    }

    @Test
    public void calculateFdRates() throws Exception {
        UtilsOds.loadSpreadsheet(filePath);
        int rows = UtilsOds.getRowCount(filePath, "Sheet1");
        log("Total rows found in the spreadsheet: " + rows);
        for (int i = 1; i <rows; i++) {
            // Fetch data for the current row
            String principle = UtilsOds.getCellData(filePath, "Sheet1", i, 0);
            String roi = UtilsOds.getCellData(filePath, "Sheet1", i, 1);
            String per1 = UtilsOds.getCellData(filePath, "Sheet1", i, 2);
            String per2 = UtilsOds.getCellData(filePath, "Sheet1", i, 3);
            String frequency = UtilsOds.getCellData(filePath, "Sheet1", i, 4);
            String exp_maturityValue = UtilsOds.getCellData(filePath, "Sheet1", i, 5);

            int period1 = (int) Math.round(Double.parseDouble(per1));

            log("Performing FD calculation for row: " + i);
            user.calculateRateOfInterest(principle, roi, String.valueOf(period1), per2, frequency);

            String actualMaturityAmt = user.getMaturity();
            log("Expected Maturity: " + exp_maturityValue + ", Actual Maturity: " + actualMaturityAmt);

            if (Double.parseDouble(exp_maturityValue) == Double.parseDouble(actualMaturityAmt)) {
                log("Test passed for row: " + i);
                UtilsOds.setCellData(filePath, "Sheet1", i, 7, "Passed");
            } else {
                log("Test failed for row: " + i);
                UtilsOds.setCellData(filePath, "Sheet1", i, 7, "Failed");
                if (actualMaturityAmt == null || actualMaturityAmt.isEmpty()) {
                    UtilsOds.setCellData(filePath, "Sheet1", i, 8, "Failed");
                }
            }
            user.clickOnClearBtn();
        }
    }
}
