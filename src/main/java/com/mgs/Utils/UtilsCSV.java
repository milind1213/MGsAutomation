package com.mgs.Utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class UtilsCSV {
    // Method to read data from a CSV file and return it as a List of Maps<String, Object>
    public static List<Map<String, Object>> readDataFromCsv(String csvFilePath) throws IOException {
        List<Map<String, Object>> dataList = new ArrayList<>();

        try (Reader reader = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                Map<String, Object> rowMap = new HashMap<>();

                // Iterate through each column (header) and populate the rowMap with dynamically parsed values
                for (String header : record.toMap().keySet()) {
                    rowMap.put(header, parseValue(record.get(header)));
                }
                dataList.add(rowMap);
            }
        }

        return dataList;
    }

    // Method to write data to a CSV file, given a List of Maps<String, Object>
    public static void writeDataToCsv(List<Map<String, Object>> dataList, String csvFilePath) throws IOException {
        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("No data to write to CSV");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(dataList.get(0).keySet().toArray(new String[0])))) {

            for (Map<String, Object> rowMap : dataList) {
                csvPrinter.printRecord(rowMap.values());
            }
        }
    }

    // Helper method to parse values from String to their correct type (Integer, Double, etc.)
    private static Object parseValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        // Try to parse as Integer
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Not an Integer
        }

        // Try to parse as Double
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            // Not a Double
        }

        // Otherwise, return as String
        return value;
    }
}
