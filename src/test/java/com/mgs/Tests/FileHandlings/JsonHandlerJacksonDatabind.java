package com.mgs.Tests.FileHandlings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgs.Pages.RestPage.POJO.CSProfile;
import com.mgs.Pages.RestPage.POJO.OrderDetails;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonHandlerJacksonDatabind {
    @Test(priority = 1)
    public void fixedDepositDetailValidating() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        // Read JSON into a Map
        Map<String, Object> customer = objectMapper.readValue(new File(filePath), Map.class);
        // Extract Customer Details
        System.out.println("Customer Name : " + customer.get("customerName"));
        System.out.println("Risk Capacity : " + customer.get("riskCapacity"));
        System.out.println("Investment Strategy: " + customer.get("investmentStrategy"));
        System.out.println("Total Investment : " + customer.get("totalInvestment"));

        long maturityAmount = Long.parseLong(customer.get("maturityAmount").toString());
        System.out.println("Total Maturity : " + maturityAmount);

        // Extract Deposit Details
        long calculatedMaturity = 0;
        List<Map<String, Object>> depositDetails = (List<Map<String, Object>>) customer.get("depositDetails");
        for (Map<String, Object> deposit : depositDetails) {
            // Extracting deposit details
            long principle = Long.parseLong(deposit.get("principle").toString());
            long rateOfInterest = Long.parseLong(deposit.get("rateOfInterest").toString());
            long period = Long.parseLong(deposit.get("period").toString());
            String periodType = (String) deposit.get("periodType");
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
        Assert.assertEquals(maturityAmount, calculatedMaturity);
    }


    @Test(priority = 2)  // Using POJO
    public void fixedDepositDetailValidatingUsingPOJO() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        CSProfile customer = objectMapper.readValue(new File(filePath), CSProfile.class);

        // Extract Customer Details
        System.out.println("Customer Name : " + customer.getCustomerName());
        System.out.println("Risk Capacity : " + customer.getRiskCapacity());
        System.out.println("Investment Strategy: " + customer.getInvestmentStrategy());
        System.out.println("Total Investment : " + customer.getTotalInvestment());
        long maturityAmount = customer.getMaturityAmount();
        System.out.println("Total Maturity : " + maturityAmount);
        // Extract Deposit Details and calculate total maturity
        long calculatedMaturity = 0;
        for (CSProfile.DepositDetail deposit : customer.getDepositDetails()) {
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


    @Test(priority=3) //Using Jackson Databind
    public static void orderDetailsRadWritePOJO() throws IOException {
        String path = System.getProperty("user.dir") + "/TestData/OrderDetails.json";
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDetails orderDetails = objectMapper.readValue(new File(path), OrderDetails.class);

        String orderId = orderDetails.getOrderId();
        String customerName = orderDetails.getCustomerName();
        System.out.println("CustomerName:"+customerName +", OrderId:"+orderId);

        int sumAmount=0,numberOfItems=0;
        for (OrderDetails.Item item : orderDetails.getItems()) {
            numberOfItems = numberOfItems + item.getQuantity();
            sumAmount = sumAmount+ item.getTotalPrice();
        }
        System.out.println("NumberOfItems:"+ numberOfItems+ ", PaidAmount:"+ sumAmount);

        for (OrderDetails.Item item : orderDetails.getItems()) {
            item.setQuantity(3);
            int num =  item.getQuantity();
            System.out.println("NumberOfItems:"+ num);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), orderDetails);
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
