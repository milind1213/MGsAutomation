package com.mgs.Utils;

import com.github.miachm.sods.SpreadSheet;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.Range;

import java.io.File;
import java.io.FileOutputStream;

public class UtilsOds {
    public static SpreadSheet loadSpreadsheet(String odsFile) throws Exception {
        File file = new File(odsFile);
        if (!file.exists()) {
            throw new Exception("ODS file not found: " + odsFile);
        }
        return new SpreadSheet(file);
    }

    public static int getRowCount(String odsFile, String sheetName) throws Exception {
        SpreadSheet spreadsheet = loadSpreadsheet(odsFile);
        Sheet sheet = spreadsheet.getSheet(sheetName);
        if (sheet == null) {
            throw new Exception("Sheet not found: " + sheetName);
        }
        return sheet.getDataRange().getLastRow(); // Get total number of rows
    }

    public static int getCellCount(String odsFile, String sheetName, int rownum) throws Exception {
        SpreadSheet spreadsheet = loadSpreadsheet(odsFile);
        Sheet sheet = spreadsheet.getSheet(sheetName);
        if (sheet == null) {
            throw new Exception("Sheet not found: " + sheetName);
        }
        return sheet.getDataRange().getLastColumn(); // Get total number of columns in the sheet
    }

    public static String getCellData(String odsFile, String sheetName, int rownum, int colnum) throws Exception {
        SpreadSheet spreadsheet = loadSpreadsheet(odsFile);
        Sheet sheet = spreadsheet.getSheet(sheetName);
        if (sheet == null) {
            throw new Exception("Sheet not found: " + sheetName);
        }

        Range range = sheet.getDataRange();
        if (rownum < 0 || rownum >= range.getLastRow()) {
            throw new Exception("Invalid row number: " + rownum);
        }
        if (colnum < 0 || colnum >= range.getLastColumn()) {
            throw new Exception("Invalid column number: " + colnum);
        }
        Object cellValue = range.getCell(rownum, colnum).getValue();
        return (cellValue != null) ? cellValue.toString() : "";
    }

    public static void setCellData(String odsFile, String sheetName, int rownum, int colnum, String data) throws Exception {
        SpreadSheet spreadsheet = loadSpreadsheet(odsFile);
        Sheet sheet = spreadsheet.getSheet(sheetName);
        if (sheet == null) {
            throw new Exception("Sheet not found: " + sheetName);
        }
        // Set the value in the specified row and column
        sheet.getRange(rownum, colnum).setValue(data);

        // Save the file after updating
        try (FileOutputStream fos = new FileOutputStream(new File(odsFile))) {
            spreadsheet.save(fos);
        }
    }

}
