package com.mgs.Tests.FileHandlings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mgs.Pages.RestPage.POJO.OrderDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class JsonHandleGsonSimpleJson {
    @Test //Simple Json
    public void writeJSON() throws IOException {
        String path = System.getProperty("user.dir") + "/TestData/studentDetails.json";
        FileWriter file = new FileWriter(path);

        JSONObject student1 = new JSONObject();
        student1.put("studentName", "John");
        student1.put("Grade", "5th");
        student1.put("Location", "16th Avenue");

        JSONObject student2 = new JSONObject();
        student2.put("studentName", "Anna");
        student2.put("Grade", "5th");
        student2.put("Location", "16th Avenue");

        System.out.println(student1.toJSONString());
        System.out.println(student2.toJSONString());

        JSONArray studentDetails = new JSONArray();
        studentDetails.add(student1);
        studentDetails.add(student2);

        System.out.println(studentDetails.toJSONString());

        JSONObject details = new JSONObject();
        details.put("studentDetails", studentDetails);
        System.out.println(details.toJSONString());

        file.write(details.toJSONString());
        file.flush();
    }

    @Test
    public void readJSON() throws IOException, ParseException {
        String filepath = System.getProperty("user.dir") + "/TestData/testData.json";
        FileReader file = new FileReader(filepath);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(file);

        System.out.println(json.toJSONString());
        JSONArray testData = (JSONArray) json.get("testdata");

       /* System.out.println(testData.toJSONString());
        JSONObject logintest = (JSONObject) testData.get(0);
        System.out.println(logintest.toJSONString());
        System.out.println(logintest.get("testName"));
        JSONArray loginTestSet = (JSONArray) logintest.get("data");
        JSONObject LoginTestSetData1 = (JSONObject) loginTestSet.get(1);
        System.out.println(LoginTestSetData1.get("browser"));
        */

        for (int i = 0; i < testData.size(); i++) {
            JSONObject testCase = (JSONObject) testData.get(i);
            System.out.println("Test Case Name is --  " + testCase.get("testName"));
            JSONArray testCaseData = (JSONArray) testCase.get("data");
            for (int j = 0; j < testCaseData.size(); j++) {
                JSONObject currentTestData = (JSONObject) testCaseData.get(j);
                Set<String> keys = currentTestData.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = (String) currentTestData.get(key);
                    System.out.println(key + " -- " + value);
                }
            }
        }

    }

    @Test //Simple-Json
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

    @Test  // Using Simple-Json
    public void fixedDepositDetailsValidatingUsingGson() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + "/TestData/CSProfile.json"; // Path to the JSON file
        JSONParser jsonParser = new JSONParser();     // Initialize JSON parser
        FileReader file = new FileReader(filePath); // Read the JSON file
        Object obj = jsonParser.parse(file);        // Parse JSON data
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


    @Test //Using Gson
    public static void orderDetailRadWritePOJO() throws IOException {
        String path = System.getProperty("user.dir") + "/TestData/OrderDetails.json";
        Gson gson = new Gson();
        OrderDetails orderDetails = gson.fromJson(new FileReader(path), OrderDetails.class);
        String orderId = orderDetails.getOrderId();
        System.out.println(orderId);

        for (OrderDetails.Item item : orderDetails.getItems()) {
            System.out.println("Product ID: " + item.getProductId());
            System.out.println("Product Name: " + item.getProductName());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price per Unit: " + item.getPricePerUnit());
            System.out.println("Total Price: " + item.getTotalPrice());
        }

        for (OrderDetails.Item item : orderDetails.getItems()) {
            item.setQuantity(10);
            int num = item.getQuantity();
            System.out.println("NumberOfItems:" + num);
        }
        // Write the updated object back to the JSON file using Gson
        /*try (FileWriter writer = new FileWriter(path)) {
            Gson gsonWriter = new GsonBuilder().setPrettyPrinting().create();
            gsonWriter.toJson(orderDetails, writer); // Write updated object back to file
        }*/

        try (FileWriter writer = new FileWriter(path)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(orderDetails, writer);
        }
        System.out.println("Updated JSON file saved successfully.");
    }

    @Test // Simple Json Write
    public void orderDetailReadWriteSimpleJson() throws IOException, ParseException {
        String path = System.getProperty("user.dir") + "/TestData/OrderDetails.json";
        // Parse the JSON file
        JSONParser jsonParser = new JSONParser();
        JSONObject orderDetails = (JSONObject) jsonParser.parse(new FileReader(path));

        // Read Order ID
        String orderId = (String) orderDetails.get("orderId");
        System.out.println("Order ID: " + orderId);

        // Read and process items array
        JSONArray itemsArray = (JSONArray) orderDetails.get("items");

        for (Object itemObj : itemsArray) {
            JSONObject item = (JSONObject) itemObj;
            System.out.println("Product ID: " + item.get("productId"));
            System.out.println("Product Name: " + item.get("productName"));
            System.out.println("Quantity: " + item.get("quantity"));
            System.out.println("Price per Unit: " + item.get("pricePerUnit"));
            System.out.println("Total Price: " + item.get("totalPrice"));
        }
        // Modify the quantity of each item
        for (Object itemObj : itemsArray) {
            JSONObject item = (JSONObject) itemObj;
            item.put("quantity", 10); // Set quantity to 10
            System.out.println("Updated Quantity: " + item.get("quantity"));
        }

        // Write the updated JSON object back to the file
        try (FileWriter file = new FileWriter(path)) {
            file.write(orderDetails.toJSONString());
            file.flush();
        }
        System.out.println("Updated JSON file saved successfully.");
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


