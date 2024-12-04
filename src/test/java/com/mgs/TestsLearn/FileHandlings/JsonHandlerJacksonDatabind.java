package com.mgs.TestsLearn.FileHandlings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mgs.Pages.RestPage.POJO.CustomerProfile;
import com.mgs.Pages.RestPage.POJO.OrderDetails;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonHandlerJacksonDatabind {
    @Test
    public void writeJSON() throws IOException {
        String path = System.getProperty("user.dir") + "/TestData/studentDetails.json";
        File jsonFile = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode student1 =  objectMapper.createObjectNode();
        student1.put("studentName", "John");
        student1.put("Grade", "5th");
        student1.put("Location", "16th Avenue");

        ObjectNode student2 =  objectMapper.createObjectNode();
        student2.put("studentName", "John");
        student2.put("Grade", "5th");
        student2.put("Location", "16th Avenue");

        System.out.println(objectMapper.writeValueAsString(student1));
        System.out.println(objectMapper.writeValueAsString(student2));

        ArrayNode studentDetails = objectMapper.createArrayNode();
        studentDetails.add(student1);
        studentDetails.add(student2);
        System.out.println(objectMapper.writeValueAsString(studentDetails));

        ObjectNode details = objectMapper.createObjectNode();
        details.set("studentDetails", studentDetails);
        System.out.println(objectMapper.writeValueAsString(details));

        // Write JSON
        objectMapper.writeValue(jsonFile, details);
        System.out.println("JSON written to file: " + jsonFile.getAbsolutePath());
    }


    @Test(priority = 1)
    public void fixedDepositDetailValidating() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CustomerProfile.json";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> customer = objectMapper.readValue(new File(filePath), Map.class);

        System.out.println("Customer Name : " + customer.get("customerName"));
        System.out.println("Risk Capacity : " + customer.get("riskCapacity"));
        System.out.println("Investment Strategy: " + customer.get("investmentStrategy"));
        System.out.println("Total Investment : " + customer.get("totalInvestment"));

        long maturityAmount = Long.parseLong(customer.get("maturityAmount").toString());
        System.out.println("Total Maturity : " + maturityAmount);

        long calculatedMaturity = 0;
        List<Map<String, Object>> depositDetails = (List<Map<String, Object>>) customer.get("depositDetails");
        for (Map<String, Object> deposit : depositDetails) {
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

            long calculatedMaturityValue = calculateMaturityValue(principle, rateOfInterest, period, interestType);
            calculatedMaturity += calculatedMaturityValue;
        }
        System.out.println("Displaying Maturity: " + maturityAmount + " Calculated Maturity: " + calculatedMaturity);
        Assert.assertEquals(maturityAmount, calculatedMaturity);
    }

    @Test(priority = 2)
    public void getBankAccountNumbers() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CustomerProfile.json"; // Path to the JSON file
        ObjectMapper obj = new ObjectMapper();
        Map<String, Object> customer = obj.readValue(new File(filePath), Map.class);
        String customerName = customer.get("customerName").toString();
        System.out.println("Customer Name : " + customerName);

        List<Map<String, Object>> depositDetails = (List<Map<String, Object>>) customer.get("depositDetails");
        for (Map<String, Object> deposit : depositDetails) {
            Map<String, Object> bankDetails = (Map<String, Object>) deposit.get("bankDetails");
            String accountNumber = bankDetails.get("accountNumber").toString();
            String bankName = bankDetails.get("name").toString();
            System.out.println("Account Number :" + accountNumber + " Bank Name : " + bankName);

            Map<String, Object> history = (Map<String, Object>) deposit.get("history");
            List<String> transactionIDs = (List<String>) history.get("transactionIDs");
            System.out.println("Transaction IDs for deposit: " + deposit.get("principle"));
            for (String transactionID : transactionIDs) {
                System.out.println(transactionID);
            }

            List<Map<String, Object>> statuses = (List<Map<String, Object>>) history.get("statuses");
            for (Map<String, Object> sts : statuses) {
                String status = sts.get("status").toString();
                System.out.println("Account Status : " + status);
            }
            System.out.println(); // Just for better formatting
        }
    }

    @Test(priority = 3)  // Using POJO
    public void fixedDepositDetailValidatingUsingPOJO() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CustomerProfile.json";
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerProfile customer = objectMapper.readValue(new File(filePath), CustomerProfile.class);

        System.out.println("Customer Name : " + customer.getCustomerName());
        System.out.println("Risk Capacity : " + customer.getRiskCapacity());
        System.out.println("Investment Strategy: " + customer.getInvestmentStrategy());
        System.out.println("Total Investment : " + customer.getTotalInvestment());
        long maturityAmount = customer.getMaturityAmount();
        System.out.println("Total Maturity : " + maturityAmount);

        // Extract Deposit Details and calculate total maturity
        long calculatedMaturity = 0;
        for (CustomerProfile.DepositDetail deposit : customer.getDepositDetails()) {
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


    @Test(priority = 4)
    public void getBankAccountNumbersPOJO() throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/CustomerProfile.json"; // Path to the JSON file
        ObjectMapper obj = new ObjectMapper();
        CustomerProfile customer = obj.readValue(new File(filePath), CustomerProfile.class);
        String customerName = customer.getCustomerName();
        System.out.println("Customer Name : " + customerName);
        List<CustomerProfile.DepositDetail> deposits = customer.getDepositDetails();
        for (CustomerProfile.DepositDetail deposit : deposits) {
            CustomerProfile.DepositDetail.BankDetails bankDetails = deposit.getBankDetails();
            System.out.println("AccountNumber : " + bankDetails.getAccountNumber() + " BankName : " + bankDetails.getName());

            CustomerProfile.DepositDetail.History history = deposit.getHistory();  //System.out.println("Transaction IDs : " +history.getTransactionIDs());
            List<String> transactionIDs = history.getTransactionIDs();
            for (String id : transactionIDs) {
                System.out.println("Transaction ID: " + id);
            }

            List<CustomerProfile.DepositDetail.History.Status> statusList = history.getStatuses();
            for (CustomerProfile.DepositDetail.History.Status sts : statusList) {
                System.out.println("Transaction ID: " + sts.getStatus());
            }
            System.out.println();
        }
    }

    @Test(priority = 5) //Using Jackson Databind
    public static void orderDetailsRadWritePOJO() throws IOException {
        String path = System.getProperty("user.dir") + "/TestData/OrderDetails.json";
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDetails orderDetails = objectMapper.readValue(new File(path), OrderDetails.class);
        System.out.println("Name:"+orderDetails.getCustomerName()+", OrderId:"+orderDetails.getOrderId());
        int sumAmount = 0, numberOfItems = 0;
        for (OrderDetails.Item item : orderDetails.getItems()) {
            numberOfItems = numberOfItems + item.getQuantity();
            sumAmount = sumAmount + item.getTotalPrice();
        }
        System.out.println("NumberOfItems:" + numberOfItems + ", PaidAmount:" + sumAmount);

        for (OrderDetails.Item item : orderDetails.getItems()) {
            item.setQuantity(3);
            int num = item.getQuantity();
            System.out.println("NumberOfItems:" + num);
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
