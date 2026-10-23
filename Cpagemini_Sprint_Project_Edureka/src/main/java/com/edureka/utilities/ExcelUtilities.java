package com.edureka.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtilities {

    private ExcelUtilities() {
    }

    private static final String FILE_PATH =System.getProperty("user.dir")+ "/src/test/resources/Edureka_TestCases.xlsx";

    public static String getCellData(
            String sheetName, int rowIndex, int columnIndex)
            throws IOException {

        try (FileInputStream input =new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(input)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row row = sheet.getRow(rowIndex);

            if (row == null || row.getCell(columnIndex) == null) {
                return "";
            }

            return new DataFormatter().formatCellValue(row.getCell(columnIndex));
        }
    }

    public static String getDataByHeader(
            String sheetName, int dataRowIndex, String header)
            throws IOException {

        try (FileInputStream input =new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(input)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IllegalArgumentException("Header row is missing.");
            }

            DataFormatter formatter = new DataFormatter();
            int columnIndex = -1;

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String cellValue = formatter.formatCellValue(
                        headerRow.getCell(i)).trim();

                if (cellValue.equalsIgnoreCase(header.trim())) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new IllegalArgumentException("Column not found: " + header);
            }

            Row dataRow = sheet.getRow(dataRowIndex);

            if (dataRow == null|| dataRow.getCell(columnIndex) == null) {
                return "";
            }

            return formatter.formatCellValue(dataRow.getCell(columnIndex));
        }
    }
    
    public static String getDataByKey(
            String sheetName,
            String keyHeader,
            String keyValue,
            String targetHeader) throws IOException {

        try (FileInputStream input = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(input)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);

            int keyColumn = -1;
            int targetColumn = -1;

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String heading = formatter.formatCellValue(
                        headerRow.getCell(i)).trim();

                if (heading.equalsIgnoreCase(keyHeader.trim())) {
                    keyColumn = i;
                }

                if (heading.equalsIgnoreCase(targetHeader.trim())) {
                    targetColumn = i;
                }
            }

            if (keyColumn == -1 || targetColumn == -1) {
                throw new IllegalArgumentException("Required Excel column was not found.");
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String key = formatter.formatCellValue(row.getCell(keyColumn)).trim();

                if (key.equalsIgnoreCase(keyValue.trim())) {
                    return formatter.formatCellValue(row.getCell(targetColumn)).trim();
                }
            }

            throw new IllegalArgumentException("Test case not found: " + keyValue);
        }
    }
   
}
