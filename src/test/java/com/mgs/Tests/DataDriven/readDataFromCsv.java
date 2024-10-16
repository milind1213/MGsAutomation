package com.mgs.Tests.DataDriven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.FDCalculatorPage;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.UtilsCSV;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.mgs.Utils.FileUtil.getProperty;

public class readDataFromCsv extends BaseTest {
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



    @Test(priority = 2)
    public void fixedDepositDetailsValidating() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file

        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON into a Map
        Map<String, Object> csProfile = objectMapper.readValue(new File(filePath), Map.class);

        // Extract Customer Details
        System.out.println("Customer Name : " + csProfile.get("CustomerName"));
        System.out.println("Risk Capacity : " + csProfile.get("RiskCapacity"));
        System.out.println("Investment Strategy: " + csProfile.get("InvestmentStrategy"));
        System.out.println("Total Investment : " + csProfile.get("TotalInvestment"));

        long maturityAmount = ((Number) csProfile.get("MaturityAmount")).longValue();
        System.out.println("Total Maturity : " + maturityAmount);

        // Extract Deposit Details
        long calculatedMaturity = 0;
        List<Map<String, Object>> depositDetails = (List<Map<String, Object>>) csProfile.get("DepositDetails");

        for (Map<String, Object> deposit : depositDetails) {
            // Extracting deposit details
            long principle = ((Number) deposit.get("principle")).longValue();
            long rateOfInterest = ((Number) deposit.get("rateOfInterest")).longValue();
            long period = ((Number) deposit.get("period")).longValue();
            String periodType = (String) deposit.get("periodType");
            String interestType = (String) deposit.get("InterestType");
            double maturityValue = ((Number) deposit.get("maturityValue")).doubleValue();

            System.out.println("\nPrinciple: " + principle);
            System.out.println("Rate of Interest: " + rateOfInterest);
            System.out.println("Interest Type: " + interestType);
            System.out.println("Period Type: " + periodType + " Period: " + period);
            System.out.println("Maturity Value: " + maturityValue);

            // Calculate Maturity Value
            long calculatedMaturityValue = calculateMaturityValue(principle, rateOfInterest, period, interestType);
            calculatedMaturity += calculatedMaturityValue; // Update calculated maturity
        }

        System.out.println("Displaying Maturity: " + maturityAmount + " Calculated Maturity: " + calculatedMaturity);
        Assert.assertEquals(maturityAmount, calculatedMaturity);
    }

    private long calculateMaturityValue(long principle, long rateOfInterest, long period, String frequency) {
        if (frequency.equalsIgnoreCase("Simple Interest")) {
            return Math.round(principle + (principle * rateOfInterest * period) / 100.0);
        } else if (frequency.equalsIgnoreCase("Compound Interest")) {
            return Math.round(principle * Math.pow((1 + (rateOfInterest / 100.0)), period));
        } else {
            throw new IllegalArgumentException("Unsupported frequency: " + frequency);
        }
    }

}