package com.mgs.Tests.Learning;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main (String [] args) {
        List<Product> order = new ArrayList<>();
        order.add(new Product("Apples", "Perishable", 100, 6, 10, 0));
        order.add(new Product("Rice (5kg bag)", "Non-Perishable", 500, 4, 0, 5));
        order.add(new Product("Milk (1 liter)", "Perishable", 50, 10, 5, 0));

        Product highestCostProduct = null;
        double highestCost = 0;
        double totalOrderCost = 0;

        for (Product product : order) {
            double discountedCost = product.calculateDiscountedCost();
            totalOrderCost += discountedCost;
            if (discountedCost > highestCost) {
                highestCost = discountedCost;
                highestCostProduct = product;
            }
        }

        // Apply non-perishable discount if applicable
        if (totalOrderCost > 2000) {
            for (Product product : order) {
                if (product.getName().equalsIgnoreCase("Rice (5kg bag)")) {
                    double riceDiscount = product.calculateDiscountedCost() * 0.05;
                    totalOrderCost -= riceDiscount;
                }
            }
        }

        // Output results
        System.out.println("Product with the highest total cost after discounts: " +
                (highestCostProduct != null ? highestCostProduct.getName() : "None"));
        System.out.println("Total amount payable by the customer: Rs. " + totalOrderCost);
    }


    @Test
    public void checkElectronics(){
        List<ElectronicsProduct> list = new ArrayList<>();
        list.add(new ElectronicsProduct("Dell Laptop", "Laptop", 60000, 2, 8));
        list.add(new ElectronicsProduct("iPhone 15", "Smartphone", 80000, 1, 5));
        list.add(new ElectronicsProduct("USB-C Hub", "Accessory", 2000, 5, 15));
        list.add(new ElectronicsProduct("Samsung Galaxy", "Smartphone", 55000, 2, 5));

        double totalOrderCost = 0;
        ElectronicsProduct highestCostProduct = null;
        double highestCost = 0;
        boolean hasLaptop = false, hasSmartphone = false, hasAccessory = false;
        for (ElectronicsProduct product : list) {
            double discountedCost = product.calculateDiscountedCost();
            totalOrderCost += discountedCost;

            if (discountedCost > highestCost) {
                highestCost = discountedCost;
                highestCostProduct = product;
            }
            // Track categories
            if (product.getCategory().equalsIgnoreCase("Laptop")) hasLaptop = true;
            if (product.getCategory().equalsIgnoreCase("Smartphone")) hasSmartphone = true;
            if (product.getCategory().equalsIgnoreCase("Accessory")) hasAccessory = true;
        }

        // Apply category-wise discount if all categories are present
        if (hasLaptop && hasSmartphone && hasAccessory) {
            totalOrderCost -= totalOrderCost * 0.10;
        }
        // Apply flat discount for orders above Rs. 50,000
        if (totalOrderCost > 50000) {
            totalOrderCost -= 5000;
        }
        // Output results
        System.out.println("Product with the highest total cost after discounts: " +
                (highestCostProduct != null ? highestCostProduct.getName() : "None"));
        System.out.println("Total amount payable by the customer: Rs. " + totalOrderCost);
    }
}
