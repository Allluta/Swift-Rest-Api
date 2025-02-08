package com.example.swift_rest_api.service;

import com.example.swift_rest_api.model.SwiftCode;
import com.example.swift_rest_api.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwiftCodeService {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    public SwiftCode getSwiftCodeDetails(String swiftCode) {
        SwiftCode code = swiftCodeRepository.findBySwiftCode(swiftCode);
        if (code == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Swift code not found");
        }

        if (code.isHeadquarter()) {
            List<SwiftCode> branches = swiftCodeRepository.findByCountryISO2(code.getCountryISO2())
                    .stream()
                    .filter(branch -> branch.getSwiftCode().startsWith(swiftCode.substring(0, 8)) && !branch.isHeadquarter())
                    .collect(Collectors.toList());

            code.setBranches(branches);
        }

        return code;
    }

    public List<SwiftCode> getSwiftCodesByCountry(String countryISO2) {
        countryISO2 = countryISO2.trim().toUpperCase();
        return swiftCodeRepository.findByCountryISO2(countryISO2);
    }

    public void addSwiftCode(SwiftCode swiftCode) {
        if (swiftCodeRepository.existsBySwiftCode(swiftCode.getSwiftCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Swift code already exists");
        }
        swiftCodeRepository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        SwiftCode code = swiftCodeRepository.findBySwiftCode(swiftCode);
        if (code == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Swift code not found");
        }
        swiftCodeRepository.delete(code);
    }
}
