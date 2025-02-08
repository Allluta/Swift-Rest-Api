package com.example.swift_rest_api;

import com.example.swift_rest_api.util.SwiftCodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwiftRestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SwiftRestApiApplication.class, args);
	}

	@Autowired
	private SwiftCodeParser swiftCodeParser;
	@Override
	public void run(String... args) throws Exception {
		String filePath = "src/main/resources/Interns_2025_SWIFT_CODES.xlsx";
		swiftCodeParser.parseExcelFileAndStore(filePath);
		System.out.println("SWIFT codes have been successfully imported into the database.");
	}
}
