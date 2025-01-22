package com.example.swift_rest_api.util;

import com.example.swift_rest_api.model.SwiftCode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwiftCodeParser {

    public List<SwiftCode> parseExcelFile(String filePath) {
        List<SwiftCode> swiftCodes = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String countryISO2 = row.getCell(0).getStringCellValue().toUpperCase();
                String swiftCode = row.getCell(1).getStringCellValue().toUpperCase();
                String bankName = row.getCell(3).getStringCellValue();
                String address = row.getCell(4).getStringCellValue();
                String countryName = row.getCell(6).getStringCellValue().toUpperCase();
                boolean isHeadquarter = swiftCode.endsWith("XXX");

                SwiftCode swiftCodeObj = new SwiftCode();
                swiftCodeObj.setSwiftCode(swiftCode);
                swiftCodeObj.setBankName(bankName);
                swiftCodeObj.setAddress(address);
                swiftCodeObj.setCountryISO2(countryISO2);
                swiftCodeObj.setCountryName(countryName);
                swiftCodeObj.setHeadquarter(isHeadquarter);

                swiftCodes.add(swiftCodeObj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return swiftCodes;
    }
}
