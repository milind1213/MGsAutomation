package com.mgs.Tests.DataDriven;

import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.FDCalculatorPage;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.UtilsCSV;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.mgs.Utils.FileUtil.getProperty;

public class readDataFromCsvTest extends BaseTest {
    WebDriver driver;
    String csvFilePath = System.getProperty("user.dir") + "/TestData/FD.csv";  // Path to CSV file
    FDCalculatorPage user;

    @BeforeTest
    public void launchApp() {
        driver = getWebDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_FD_URL));
        user = new FDCalculatorPage(driver);
    }

    @Test(priority = 1)
    public void calculateFdRatesWithSimple() throws IOException {
        List<Map<String, Object>> fdDataList = UtilsCSV.readDataFromCsv(csvFilePath);
        for (Map<String, Object> fdData : fdDataList) {  // Iterate over each row of data
            String principle = fdData.get("Principle").toString();
            String rateOfInterest = fdData.get("RateOfInterest").toString();
            String period = fdData.get("Period").toString();
            String periodType = fdData.get("PeriodType").toString();
            String frequency = fdData.get("Frequency").toString();
            String expectedMaturityValue = fdData.get("MaturityValue").toString();

            log("Performing FD calculation for Principle: " + principle);
            user.calculateRateOfInterest(principle, rateOfInterest, period, periodType, frequency);

            String actualMaturityAmt = user.getMaturity();
            log("Expected Maturity: " + expectedMaturityValue + ", Actual Maturity: " + actualMaturityAmt);

            if (Double.parseDouble(actualMaturityAmt) == Double.parseDouble(expectedMaturityValue)) {
                log("Test passed for Principle: " + principle);
                fdData.put("Result", "Passed");  // Set the result as "Passed"
            } else {
                log("Test failed for Principle: " + principle);
                fdData.put("Result", "Failed");  // Set the result as "Failed"
            }
            user.clickOnClearBtn();
        }
        UtilsCSV.writeDataToCsv(fdDataList, csvFilePath);
    }
}