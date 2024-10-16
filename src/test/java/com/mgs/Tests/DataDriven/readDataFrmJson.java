package com.mgs.Tests.DataDriven;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgs.Pages.RestPage.POJO.CSProfile;
import com.mgs.Pages.RestPage.POJO.OrderDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class readDataFrmJson {
    @Test(priority = 1)
    public static void fixedDepositDetailsReading() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + "/TestData/FDRates.json";
        JSONParser jsonParser = new JSONParser();        // Initialize JSON parser
        FileReader reader = new FileReader(filePath);    // Read and parse the file
        Object obj = jsonParser.parse(reader);

        JSONArray jsonArray = (JSONArray) obj;  // Cast the parsed object to JSONArray as the root is an array
        for (Object jsonElement : jsonArray) {
            JSONObject jsonObject = (JSONObject) jsonElement;

            String principle = jsonObject.get("principle").toString();
            System.out.println("Principle: " + principle);

            String rateOfInterest = jsonObject.get("rateOfInterest").toString();
            System.out.println("Rate of Interest: " + rateOfInterest);

            String period = jsonObject.get("period").toString();
            System.out.println("Period: " + period);
        }
    }

    @Test(priority = 2)  // Using Simple-Gson
    public void fixedDepositDetailsValidatingUsingGson() throws IOException, org.json.simple.parser.ParseException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file
        JSONParser jsonParser = new JSONParser();     // Initialize JSON parser
        FileReader reader = new FileReader(filePath); // Read the JSON file
        Object obj = jsonParser.parse(reader);        // Parse JSON data
        JSONObject jsonObject = (JSONObject) obj;     // Cast it to JSONObject to access details
        // Extract Customer Details
        System.out.println("Customer Name : " + jsonObject.get("customerName"));
        System.out.println("Risk Capacity : " + jsonObject.get("riskCapacity"));
        System.out.println("Total Investment : " + jsonObject.get("totalInvestment"));

        long maturityAmount = Long.parseLong(jsonObject.get("maturityAmount").toString());
        System.out.println("Total Maturity : " + maturityAmount);

        // Extract Deposit Details
        long calculatedMaturity = 0;
        JSONArray depositDetails = (JSONArray) jsonObject.get("depositDetails");

        for (Object depositObj : depositDetails) {
            JSONObject deposit = (JSONObject) depositObj;

            // Extracting deposit details
            long principle = Long.parseLong(deposit.get("principle").toString());
            long rateOfInterest = Long.parseLong(deposit.get("rateOfInterest").toString());
            long period = Long.parseLong(deposit.get("period").toString());
            String periodType = deposit.get("periodType").toString();
            String interestType = (String) deposit.get("interestType");
            double maturityValue = Double.parseDouble(deposit.get("maturityValue").toString());

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
        Assert.assertEquals(maturityAmount, Math.round(calculatedMaturity));
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

    @Test(priority = 3)  // Using Jackson Databind
    public void fixedDepositDetailValidatingUsingJacksonDatabind() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file
        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON into a Map
        Map<String, Object> csProfile = objectMapper.readValue(new File(filePath), Map.class);
        // Extract Customer Details
        System.out.println("Customer Name : " + csProfile.get("customerName"));
        System.out.println("Risk Capacity : " + csProfile.get("riskCapacity"));
        System.out.println("Investment Strategy: " + csProfile.get("investmentStrategy"));
        System.out.println("Total Investment : " + csProfile.get("totalInvestment"));

        long maturityAmount = Long.parseLong(csProfile.get("maturityAmount").toString());
        System.out.println("Total Maturity : " + maturityAmount);

        // Extract Deposit Details
        long calculatedMaturity = 0;
        List<Map<String, Object>> depositDetails = (List<Map<String, Object>>) csProfile.get("depositDetails");
        for (Map<String, Object> deposit : depositDetails) {
            // Extracting deposit details
            long principle = Long.parseLong (deposit.get("principle").toString());
            long rateOfInterest = Long.parseLong(deposit.get("rateOfInterest").toString());
            long period =  Long.parseLong(deposit.get("period").toString());
            String periodType = (String) deposit.get("periodType");
            String interestType = (String) deposit.get("interestType");
            double maturityValue =  Double.parseDouble(deposit.get("maturityValue").toString());

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

    @Test(priority = 4)  // Using POJO
    public void fixedDepositDetailValidatingUsingPOJO() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        CSProfile csProfile = objectMapper.readValue(new File(filePath), CSProfile.class);

        // Extract Customer Details
        System.out.println("Customer Name : " + csProfile.getCustomerName());
        System.out.println("Risk Capacity : " + csProfile.getRiskCapacity());
        System.out.println("Investment Strategy: " + csProfile.getInvestmentStrategy());
        System.out.println("Total Investment : " + csProfile.getTotalInvestment());
        long maturityAmount = csProfile.getMaturityAmount();
        System.out.println("Total Maturity : " + maturityAmount);
        // Extract Deposit Details and calculate total maturity
        long calculatedMaturity = 0;
        for (CSProfile.DepositDetail deposit : csProfile.getDepositDetails()) {
            long principle = deposit.getPrinciple();
            long rateOfInterest = deposit.getRateOfInterest();
            long period = deposit.getPeriod();
            String periodType = deposit.getPeriodType();
            String interestType = deposit.getInterestType();
            double maturityValue = deposit.getMaturityValue();

            System.out.println("\nPrinciple: " + principle);
            System.out.println("Rate of Interest: " + rateOfInterest);
            System.out.println("Interest Type: " + interestType);
            System.out.println("Period Type: " + periodType + " Period: " + period);
            System.out.println("Maturity Value: " + maturityValue);

            long calculatedMaturityValue = calculateMaturityValue(principle, rateOfInterest, period, interestType);
            calculatedMaturity += calculatedMaturityValue; // Update calculated maturity
        }
        System.out.println("Displaying Maturity: " + maturityAmount + " Calculated Maturity: " + calculatedMaturity);
        Assert.assertEquals(maturityAmount, calculatedMaturity);
    }

    @Test
    public static void orderDetailsValidation() throws IOException {
        String path = System.getProperty("user.dir")+"/TestData/OrderDetails.json";
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDetails orderDetails = objectMapper.readValue(new File(path), OrderDetails.class);

        String orderId = orderDetails.getOrderId().toString();
        System.out.println(orderId);
    }



}


