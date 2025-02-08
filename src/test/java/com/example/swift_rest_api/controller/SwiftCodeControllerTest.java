package com.example.swift_rest_api.controller;

import com.example.swift_rest_api.model.SwiftCode;
import com.example.swift_rest_api.service.SwiftCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SwiftCodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwiftCodeService swiftCodeService;

    @InjectMocks
    private SwiftCodeController swiftCodeController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(swiftCodeController).build();
    }

    @Test
    void testGetSwiftCodeHeadquarter() throws Exception {
        SwiftCode headquarter = new SwiftCode("ABCDEF12XXX", "Test Bank", "123 Test St", "US", "UNITED STATES", true);
        SwiftCode branch = new SwiftCode("ABCDEF12XXX", "Test Bank Branch", "456 Branch St", "US", "UNITED STATES", false);

        headquarter.setBranches(List.of(branch));

        when(swiftCodeService.getSwiftCodeDetails("ABCDEF12XXX")).thenReturn(headquarter);

        mockMvc.perform(get("/v1/swift-codes/ABCDEF12XXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("ABCDEF12XXX"))
                .andExpect(jsonPath("$.isHeadquarter").value(true))
                .andExpect(jsonPath("$.branches").isArray());
    }

    @Test
    void testGetSwiftCodeBranch() throws Exception {
        SwiftCode branch = new SwiftCode("ABCDEF12XYZ", "Test Bank Branch", "456 Branch St", "US", "UNITED STATES", false);

        when(swiftCodeService.getSwiftCodeDetails("ABCDEF12XYZ")).thenReturn(branch);

        mockMvc.perform(get("/v1/swift-codes/ABCDEF12XYZ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("ABCDEF12XYZ"))
                .andExpect(jsonPath("$.isHeadquarter").value(false))
                .andExpect(jsonPath("$.branches").doesNotExist());
    }

    @Test
    void testGetSwiftCodesByCountry() throws Exception {
        SwiftCode swiftCode1 = new SwiftCode("ABCDEF12XXX", "Test Bank", "123 Test St", "US", "UNITED STATES", true);
        SwiftCode swiftCode2 = new SwiftCode("ABCDEF12XYZ", "Test Bank Branch", "456 Branch St", "US", "UNITED STATES", false);

        List<SwiftCode> swiftCodes = List.of(swiftCode1, swiftCode2);
        when(swiftCodeService.getSwiftCodesByCountry("US")).thenReturn(swiftCodes);

        mockMvc.perform(get("/v1/swift-codes/country/US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value("US"))
                .andExpect(jsonPath("$.swiftCodes").isArray())
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode").value("ABCDEF12XXX"));
    }

    @Test
    void testAddSwiftCode() throws Exception {
        SwiftCode newSwiftCode = new SwiftCode("XYZABC34XXX", "New Bank", "789 New St", "GB", "UNITED KINGDOM", true);

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSwiftCode)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Swift code added successfully"));
    }

    @Test
    void testDeleteSwiftCode() throws Exception {
        mockMvc.perform(delete("/v1/swift-codes/XYZABC34XXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Swift code deleted successfully"));
    }
}
