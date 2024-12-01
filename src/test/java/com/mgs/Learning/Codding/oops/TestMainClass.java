package com.mgs.Learning.Codding.oops;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestMainClass {
    public static void main(String[] args) {
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
    public void checkElectronics() {
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

    @Test
    public static void runcode() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.fitpeo.com/");
        driver.findElement(By.linkText("Revenue Calculator")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 400);");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]")));

        Actions act = new Actions(driver);
        int xOffset = 5;
        int expectedValue = 803;

        WebElement valueElement = driver.findElement(By.xpath("//div[@class='MuiBox-root css-19xu03j'][2]//p[2]"));
        while (true) {
            String text = valueElement.getText();
            int currentValue = Integer.parseInt(text);
            if (currentValue >= expectedValue) {
                System.out.println("Target value reached: " + currentValue);
                break;
            }
            act.clickAndHold(slider).moveByOffset(xOffset, 0).release().perform();
            Thread.sleep(200);  // Adjust delay as needed for smoother animation
        }
        driver.quit();
    }


    @Test
    public void testRun() {
        String input = "bargalone";
        String output = swapCharacters(input, 'r', 'n');
        System.out.println("Output: " + output);
    }

    static String swapCharacters(String str, char char1, char char2) {
        char[] charArray = str.toCharArray();
        int index1 = -1, index2 = -1;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == char1) {
                index1 = i;
            } else if (charArray[i] == char2) {
                index2 = i;
            }
        }
        if (index1 != -1 && index2 != -1) {
            char temp = charArray[index1];
            charArray[index1] = charArray[index2];
            charArray[index2] = temp;
        }
        return new String(charArray);
    }

}