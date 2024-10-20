package com.mgs.Tests.WebTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DynamicWebTables {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors=yes");
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1)
    public void getCpuUsageData() throws InterruptedException {
        driver.get("https://www.uitestingplayground.com/dynamictable");
        for (int j = 0; j < 3; j++) {
            driver.navigate().refresh();
            Thread.sleep(3000);
            List<WebElement> headers = driver.findElements(By.xpath("//div[@role='rowgroup'][1]//span"));
            int Name = -1;
            int CPU = -1;
            for (int i = 0; i < headers.size(); i++) {
                String headerText = headers.get(i).getText();
                if (headerText.equals("Name")) {
                    Name = i + 1;
                }
                if (headerText.equals("CPU")) {
                    CPU = i + 1;
                }
            }
            List<WebElement> rows = driver.findElements(By.xpath("//div[@role='rowgroup'][2]//div[@role='row']"));
            for (WebElement row : rows) {
                WebElement name = row.findElement(By.xpath("span[" + Name + "]"));
                WebElement cpu = row.findElement(By.xpath("span[" + CPU + "]"));
                System.out.println(name.getText() + " : " + cpu.getText());
            }
        }
    }


    @Test(priority = 2)
    public void DynamicData() throws InterruptedException {
        driver.get("https://www.uitestingplayground.com/dynamictable");
        // Locate the header row and find all headers
        List<WebElement> headers = driver.findElements(By.xpath("//div[@role='rowgroup'][1]//span"));
        int Name = -1, Memory = -1, CPU = -1, Disk = -1, Network = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText();
            if (headerText.equals("Name")) {
                Name = i + 1;
                System.out.println("Name : " + Name);
            } else if (headerText.equals("Memory")) {
                Memory = i + 1;
                System.out.println("Memory : " + Memory);
            } else if (headerText.equals("CPU")) {
                CPU = i + 1;
                System.out.println("CPU : " + CPU);
            } else if (headerText.equals("Disk")) {
                Disk = i + 1;
                System.out.println("Disk : " + Disk);
            } else if (headerText.equals("Network")) {
                Network = i + 1;
                System.out.println("Network : " + Network);
            }
        }
        // Check if all columns are found
        if (Name == -1 || Memory == -1 || CPU == -1 || Disk == -1 || Network == -1) {
            System.out.println("Error: One or more columns were not found.");
            return;
        }
        // Locate all rows in the table body
        List<WebElement> rows = driver.findElements(By.xpath("//div[@role='rowgroup'][2]/div[@role='row']"));
        for (WebElement row : rows) {
            String name = row.findElement(By.xpath("span[" + Name + "]")).getText();
            String memory = row.findElement(By.xpath("span[" + Memory + "]")).getText();
            String cpu = row.findElement(By.xpath("span[" + CPU + "]")).getText();
            String disk = row.findElement(By.xpath("span[" + Disk + "]")).getText();
            String network = row.findElement(By.xpath("span[" + Network + "]")).getText();
            // Print the extracted values
            System.out.println("Name: " + name + ", Memory: " + memory + ", CPU: " + cpu + ", Disk: " + disk + ", Network: " + network);
        }
    }

    @Test(priority = 3)
    public void DynamicsData() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.uitestingplayground.com/dynamictable");
        for (int j = 1; j <= 3; j++) {
            System.out.println("\n*********** Data Set " + j + " **************");
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='rowgroup'][2]/div[@role='row']")));
            // Locate the header row and find all headers
            List<WebElement> headers = driver.findElements(By.xpath("//div[@role='rowgroup'][1]//span"));
            int Name = -1;
            int Memory = -1;
            int CPU = -1;
            int Disk = -1;
            int Network = -1;
            // Iterate over headers to find the index of each column
            for (int i = 0; i < headers.size(); i++) {
                String headerText = headers.get(i).getText();
                if (headerText.equals("Name")) {
                    Name = i + 1;
                } else if (headerText.equals("Memory")) {
                    Memory = i + 1;
                } else if (headerText.equals("CPU")) {
                    CPU = i + 1;
                } else if (headerText.equals("Disk")) {
                    Disk = i + 1;
                } else if (headerText.equals("Network")) {
                    Network = i + 1;
                }
            }
            // Get all rows
            List<WebElement> rows = driver.findElements(By.xpath("//div[@role='rowgroup'][2]/div[@role='row']"));
            for (WebElement row : rows) {
                // Get the cell values for each column dynamically based on the column index
                String name = row.findElement(By.xpath("span[" + Name + "]")).getText();
                String memory = row.findElement(By.xpath("span[" + Memory + "]")).getText();
                String cpu = row.findElement(By.xpath("span[" + CPU + "]")).getText();
                String disk = row.findElement(By.xpath("span[" + Disk + "]")).getText();
                String network = row.findElement(By.xpath("span[" + Network + "]")).getText();

                // Print the extracted Data
                System.out.println("Name: " + name + ", Memory: " + memory + ", CPU: " + cpu + ", Disk: " + disk + ", Network: " + network);
            }
        }
    }

    @Test(priority = 4)
    public void countryWebTable() {
        driver.get("https://cosmocode.io/automation-practice-webtable/");
        List<WebElement> columns = driver.findElements(By.xpath("//tbody//tr[1]//td//strong"));
        List<WebElement> rows = driver.findElements(By.xpath("//tbody//tr"));
        for (int i = 2; i <= rows.size(); i++) {
            System.out.println(i + " : ");
            for (int j = 2; j <= columns.size(); j++) {
                WebElement data = driver.findElement(By.xpath("//tbody//tr[" + i + "]//td[" + j + "]"));
                System.out.println(data.getText());
            }
            System.out.println();
        }
        for (int i = 1; i < rows.size(); i++) {
            getData(driver, i, 2);
        }
    }

    static void getData(WebDriver driver, int row, int col) {
        String text = driver.findElement(By.xpath("//tbody//tr[" + row + "]//td[" + col + "]")).getText();
        System.out.println(text);
    }

    @Test(priority = 5)
    public void dataCode() throws InterruptedException {
        driver.get("https://webdatacommons.org/webtables/");
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        WebElement table1 = tables.get(0);
        List<WebElement> rows1 = table1.findElements(By.tagName("tr"));
        for (WebElement row : rows1) {
            List<WebElement> cells = row.findElements(By.xpath(".//th|.//td"));
            for (WebElement cell : cells) {
                System.out.print(cell.getText() + "\t");  // Print tab-separated values for better readability
            }
            System.out.println();  // Print a newline after each row
        }
        System.out.println("\n");

        //  WebTable 2
        WebElement table2 = tables.get(1);
        List<WebElement> rows2 = table2.findElements(By.tagName("tr"));
        for (WebElement row : rows2) {
            WebElement headerCell = row.findElement(By.tagName("th"));
            WebElement dataCell = row.findElement(By.tagName("td"));
            System.out.println(headerCell.getText() + ": " + dataCell.getText());
        }
    }

    @Test(priority = 6)
    public void verifyIndises() {
        // Open the webpage
        driver.manage().window().maximize();
        driver.get("https://money.rediff.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='More BSE Indices']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", ele);
        wait.until(ExpectedConditions.elementToBeClickable(ele));
        try {
            ele.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.println("Element click intercepted, clicking using JavaScript...");
            js.executeScript("arguments[0].click();", ele);
        }
        driver.findElement(By.linkText("Show More >>")).click();

        // Locate the columns and rows
        List<WebElement> columns = driver.findElements(By.xpath("//table[@id='dataTable']//thead//tr//th"));
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='dataTable']//tbody//tr"));

        // Printing All Table Data
        for (int i = 1; i <= rows.size(); i++) {  // Loop through rows
            for (int j = 1; j <= columns.size(); j++) {  // Loop through columns (<= because columns.size() gives actual count)
                WebElement cell = driver.findElement(By.xpath("//table[@id='dataTable']//tbody//tr[" + i + "]/td[" + j + "]"));
                String text = cell.getText();
                System.out.print(text + "   ");
            }
            System.out.println();  // Newline after each row
        }

        // Get Specific Row Data
        List<WebElement> specRow = driver.findElements(By.xpath("//table[@id='dataTable']//tbody//tr[5]//td"));
        for (WebElement el : specRow) {
            System.out.println(el.getText());
        }

        // Get Specific Column Data (3rd column)
        List<WebElement> specCol = driver.findElements(By.xpath("//table[@id='dataTable']//tbody//tr//td[3]"));
        List<Double> valueList = new ArrayList<>();
        for (WebElement el : specCol) {
            try {
                double value = Double.parseDouble(el.getText().replace(",", ""));
                valueList.add(value);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing value: " + el.getText());
            }
        }

        // Find the maximum value in the column
        if (!valueList.isEmpty()) {
            double maxValue = valueList.get(0);
            for (double val : valueList) {
                if (val > maxValue) {
                    maxValue = val;
                }
            }
            System.out.println("Max Last Traded Value : " + maxValue);
        } else {
            System.out.println("No values found in the column.");
        }
    }

}


