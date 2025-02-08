package com.example.swift_rest_api.controller;

import com.example.swift_rest_api.model.SwiftCode;
import com.example.swift_rest_api.service.SwiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public ResponseEntity<Object> getSwiftCode(@PathVariable String swiftCode) {
        swiftCode = swiftCode.trim();

        SwiftCode code = swiftCodeService.getSwiftCodeDetails(swiftCode);

        Map<String, Object> response = new HashMap<>();
        response.put("address", code.getAddress());
        response.put("bankName", code.getBankName());
        response.put("countryISO2", code.getCountryISO2());
        response.put("countryName", code.getCountryName());
        response.put("isHeadquarter", code.isHeadquarter());
        response.put("swiftCode", code.getSwiftCode());

        if (code.isHeadquarter()) {
            response.put("branches", code.getBranches());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<Map<String, Object>> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        countryISO2 = countryISO2.trim().toUpperCase();

        List<SwiftCode> codes = swiftCodeService.getSwiftCodesByCountry(countryISO2);
        String countryName = codes.isEmpty() ? "Unknown" : codes.get(0).getCountryName();
        Map<String, Object> response = new HashMap<>();
        response.put("countryISO2", countryISO2);
        response.put("countryName", countryName);
        response.put("swiftCodes", codes);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addSwiftCode(@RequestBody SwiftCode swiftCode) {
        swiftCodeService.addSwiftCode(swiftCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Swift code added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<Map<String, String>> deleteSwiftCode(@PathVariable String swiftCode) {
        swiftCodeService.deleteSwiftCode(swiftCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Swift code deleted successfully");
        return ResponseEntity.ok(response);
    }
}
