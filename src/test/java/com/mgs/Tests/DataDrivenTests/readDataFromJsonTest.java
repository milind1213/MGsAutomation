package com.mgs.Tests.DataDrivenTests;
import com.mgs.CommonConstants;
import com.mgs.Pages.RestPage.FdData;
import com.mgs.Pages.WebPages.FDCalculatorPage;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.UtilsJson;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.mgs.Utils.FileUtil.getProperty;
import static com.mgs.Utils.UtilsJson.readDataFromJson;
import static com.mgs.Utils.UtilsJson.readsDataFromJson;

public class readDataFromJsonTest extends BaseTest {
    WebDriver driver;
    String jsonFilePath = System.getProperty("user.dir") + "/TestData/FDRates.json";  // Path to JSON file
    FDCalculatorPage user;

    @BeforeTest
    public void launchApp() {
        driver = getWebDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_FD_URL));
        user = new FDCalculatorPage(driver);
    }

    @Test(priority = 1)
    public void calculateFdRatesUsingGsonLibrary() throws IOException {
        List<FdData> fdDataList = readDataFromJson(jsonFilePath, FdData.class);

        for (FdData fdData : fdDataList) {
            log("Performing FD calculation for Principle: " + fdData.getPrinciple());
            user.calculateRateOfInterest(
                    String.valueOf(fdData.getPrinciple()),
                    String.valueOf(fdData.getRateOfInterest()),
                    String.valueOf(fdData.getPeriod()),
                    fdData.getPeriodType(),
                    fdData.getFrequency()
            );
            String actualMaturityAmt = user.getMaturity();
            log("Expected Maturity: " + fdData.getMaturityValue() + ", Actual Maturity: " + actualMaturityAmt);

            if (Double.parseDouble(actualMaturityAmt) == fdData.getMaturityValue()) {
                log("Test passed for Principle: " + fdData.getPrinciple());
                fdData.setResult("Passed");  // Set the result as "Passed"
            } else {
                log("Test failed for Principle: " + fdData.getPrinciple());
                fdData.setResult("Failed");  // Set the result as "Failed"
            }
            user.clickOnClearBtn();
        }
        UtilsJson.writeDataToJson(fdDataList, jsonFilePath);
    }

    @Test(priority = 2)
    public void calculateFdRatesUsingJacksonDatabindLibrary() throws IOException {
        List<FdData> fdDataList = UtilsJson.readsDataFromJson(jsonFilePath, FdData.class);
        for (FdData fdData : fdDataList) {
            log("Performing FD calculation for Principle: " + fdData.getPrinciple());

            user.calculateRateOfInterest(
                    String.valueOf(fdData.getPrinciple()),
                    String.valueOf(fdData.getRateOfInterest()),
                    String.valueOf(fdData.getPeriod()),
                    fdData.getPeriodType(),
                    fdData.getFrequency()
            );

            String actualMaturityAmt = user.getMaturity();
            log("Expected Maturity: " + fdData.getMaturityValue() + ", Actual Maturity: " + actualMaturityAmt);
            if (Double.parseDouble(actualMaturityAmt) == fdData.getMaturityValue()) {
                log("Test passed for Principle: " + fdData.getPrinciple());
                fdData.setResult("Passed");  // Set the result as "Passed"
            } else {
                log("Test failed for Principle: " + fdData.getPrinciple());
                fdData.setResult("Failed");  // Set the result as "Failed"
            }
            user.clickOnClearBtn();
        }
        UtilsJson.writesDataToJson(fdDataList, jsonFilePath);
    }
}
