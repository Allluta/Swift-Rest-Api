package com.example.swift_rest_api.util;

import com.example.swift_rest_api.model.SwiftCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwiftCodeParserTest {

    @Test
    void parseExcelFile_shouldParseCorrectly() {
        SwiftCodeParser parser = new SwiftCodeParser();
        String filePath = "src/test/resources/Interns_2025_SWIFT_CODES.xlsx";

        List<SwiftCode> swiftCodes = parser.parseExcelFile(filePath);

        assertNotNull(swiftCodes, "The SWIFT codes list should not be null");
        assertFalse(swiftCodes.isEmpty(), "The SWIFT codes list should not be empty");

        SwiftCode firstCode = swiftCodes.get(0);
        assertEquals("AL", firstCode.getCountryISO2());
        assertEquals("AAISALTRXXX", firstCode.getSwiftCode());
        assertEquals("UNITED BANK OF ALBANIA SH.A", firstCode.getBankName());
        assertEquals("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023", firstCode.getAddress());
        assertEquals("ALBANIA", firstCode.getCountryName());
        assertTrue(firstCode.isHeadquarter());
    }
}
