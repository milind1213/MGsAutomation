package com.mgs.Learning.Codding.oops;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
class Product {
    private String name;
    private String category;
    private double pricePerUnit;
    private int quantity;
    private double bulkDiscount;
    private double nonPerishableDiscount;

    public Product(String name, String category, double pricePerUnit, int quantity, double bulkDiscount, double nonPerishableDiscount)
    {
        this.name = name;
        this.category = category;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.bulkDiscount = bulkDiscount;
        this.nonPerishableDiscount = nonPerishableDiscount;
    }

    public double calculateDiscountedCost()
    {
        double totalCost = pricePerUnit * quantity;
        if (category.equalsIgnoreCase("Perishable") && quantity > 5) {
            totalCost -= totalCost * (bulkDiscount / 100);
        } else if (category.equalsIgnoreCase("Non-Perishable")) {
            totalCost -= totalCost * (nonPerishableDiscount / 100);
        }
        return totalCost;
    }
}
