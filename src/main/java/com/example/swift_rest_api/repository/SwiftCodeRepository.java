package com.example.swift_rest_api.repository;

import com.example.swift_rest_api.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwiftCodeRepository extends JpaRepository<SwiftCode, Long> {
    List<SwiftCode> findByCountryISO2(String countryISO2);
    SwiftCode findBySwiftCode(String swiftCode);
    boolean existsBySwiftCode(String swiftCode);

}
