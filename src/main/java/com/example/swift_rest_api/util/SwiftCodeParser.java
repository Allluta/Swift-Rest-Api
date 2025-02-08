package com.example.swift_rest_api.util;

import com.example.swift_rest_api.model.SwiftCode;
import com.example.swift_rest_api.repository.SwiftCodeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwiftCodeParser {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    public List<SwiftCode> parseExcelFileAndStore(String filePath) {
        List<SwiftCode> swiftCodes = parseExcelFile(filePath);

        List<String> existingSwiftCodes = swiftCodeRepository.findAll()
                .stream()
                .map(SwiftCode::getSwiftCode)
                .collect(Collectors.toList());

        for (SwiftCode swiftCode : swiftCodes) {
            if (!existingSwiftCodes.contains(swiftCode.getSwiftCode())) {
                swiftCodeRepository.save(swiftCode);
            }
        }

        return swiftCodes;
    }

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
