package com.mgs.Tests.Learning;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
class ElectronicsProduct {
    private String name;
    private String category;
    private double pricePerUnit;
    private int quantity;
    private double bulkDiscount;

    public ElectronicsProduct(String name, String category, double pricePerUnit, int quantity, double bulkDiscount) {
        this.name = name;
        this.category = category;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.bulkDiscount = bulkDiscount;
    }

    public double calculateDiscountedCost() {
        double totalCost = pricePerUnit * quantity;
        if (quantity > 3) {
            totalCost -= totalCost * (bulkDiscount / 100);
        }
        return totalCost;
    }
}
