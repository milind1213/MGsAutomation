package com.mgs.TestsLearn.FileHandlings;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WriteExcelFile {
    public static void main(String[] args) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet samplesheet = workbook.createSheet("SampleSheet");
        Map<String, Object[]> dataSet = new TreeMap<String, Object[]>();
        dataSet.put("1", new Object[] {"ID", "NAME", "Company"});
        dataSet.put("2", new Object[] {"1", "James", "PertLine Inc"});
        dataSet.put("3", new Object[] {"2", "Maria", "SumoLogic Inc"});
        dataSet.put("4", new Object[] {"3", "Peter", "Siemens Corp."});
        dataSet.put("5", new Object[] {"4", "Julia", "Google Inc"});
        dataSet.put("6", new Object[] {"5", "Ajay", "FaceBook Inc"});

        Set<String> set = dataSet.keySet();
        int rownum = 0;
        for (String key : set)
        {
            Row row = samplesheet.createRow(rownum++);
            Object[] data = dataSet.get(key);
            int cellNum = 0;
            for (Object value : data) {
                Cell cell = row.createCell(cellNum++);
                if (value instanceof String)
                    cell.setCellValue((String)value);
                else if(value instanceof Integer)
                    cell.setCellValue((Integer)value);
            }
        }
         try
        {
            FileOutputStream writeFile = new FileOutputStream("sampleTest.xlsx");
            workbook.write(writeFile);
            writeFile.close();
            System.out.println("Sample Excel file is being created Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
