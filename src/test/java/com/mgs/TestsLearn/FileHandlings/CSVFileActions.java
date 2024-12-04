package com.mgs.TestsLearn.FileHandlings;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CSVFileActions {

    public static void writeCSVFile(String path) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.append("Name,Age,City,Email \n");
            writer.append("John Doe,25,New York,johndoe@example.com \n");
            writer.append("Jane Doe,30,Los Angeles,janedoe@example.com \n");
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readCSVfile(String path) throws IOException {
        // Approach 1
       BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine())!= null) {
            String[] data = line.split(",");
            for (String a : data){
                System.out.print(a + "\t");
            }
            System.out.println();
        }
        br.close();

        // Approach 2
            File file = new File(path);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line1 = scanner.nextLine();
                    // You can now process the line
                    System.out.println("Line read: " + line1);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("File format error or end of file reached: " + e.getMessage());
            }

        // Approach 3

    }


    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"\\TestData\\emp.csv";
       // writeCSVFile(path);
        System.out.println("CSV file has been created successfully.");
        try {
            readCSVfile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
