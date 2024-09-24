package com.mgs.Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilsCSV {
    public static List<String[]> readCsv(String csvFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        List<String[]> data = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            data.add(values);
        }

        br.close();
        return data;
    }
    public static void writeCsv(String csvFile, List<String[]> data) throws IOException {
        FileWriter csvWriter = new FileWriter(csvFile);

        for (String[] rowData : data) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
