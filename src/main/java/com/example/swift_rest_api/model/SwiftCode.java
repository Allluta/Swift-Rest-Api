package com.example.swift_rest_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.util.List;

@Entity
public class SwiftCode {
    @Id
    private String swiftCode;
    private String bankName;
    private String address;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;

    @Transient
    private List<SwiftCode> branches;

    public SwiftCode() {
    }

    public SwiftCode(String swiftCode, String bankName, String address, String countryISO2, String countryName, boolean isHeadquarter) {
        this.swiftCode = swiftCode;
        this.bankName = bankName;
        this.address = address;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
    }

    public SwiftCode(String swiftCode, String bankName, String address, String countryISO2, String countryName, boolean isHeadquarter, List<SwiftCode> branches) {
        this(swiftCode, bankName, address, countryISO2, countryName, isHeadquarter);
        this.branches = branches;
    }
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public List<SwiftCode> getBranches() {
        return branches;
    }

    public void setBranches(List<SwiftCode> branches) {
        this.branches = branches;
    }
    @Override
    public String toString() {
        return "SwiftCode{" +
                "swiftCode='" + swiftCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", address='" + address + '\'' +
                ", countryISO2='" + countryISO2 + '\'' +
                ", countryName='" + countryName + '\'' +
                ", isHeadquarter=" + isHeadquarter +
                '}';
    }
}
