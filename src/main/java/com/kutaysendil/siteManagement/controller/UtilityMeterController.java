package com.kutaysendil.siteManagement.controller;

import com.kutaysendil.siteManagement.dto.request.MeterReadingUpdateRequest;
import com.kutaysendil.siteManagement.dto.request.UtilityMeterCreateRequest;
import com.kutaysendil.siteManagement.dto.response.UtilityMeterResponse;
import com.kutaysendil.siteManagement.service.UtilityMeterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/utility-meters")
@RequiredArgsConstructor
public class UtilityMeterController {

    private final UtilityMeterService utilityMeterService;

    @PostMapping
    @PreAuthorize("hasAuthority('UTILITY_METER_CREATE')")
    public ResponseEntity<UtilityMeterResponse> createMeter(@RequestBody @Valid UtilityMeterCreateRequest request) {
        return ResponseEntity.ok(utilityMeterService.createMeter(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('UTILITY_METER_READ')")
    public ResponseEntity<Page<UtilityMeterResponse>> getAllMeters(Pageable pageable) {
        return ResponseEntity.ok(utilityMeterService.getAllMeters(pageable));
    }

    @GetMapping("/property/{propertyId}")
    @PreAuthorize("hasAuthority('UTILITY_METER_READ')")
    public ResponseEntity<List<UtilityMeterResponse>> getPropertyMeters(@PathVariable Long propertyId) {
        return ResponseEntity.ok(utilityMeterService.getPropertyMeters(propertyId));
    }

    @PutMapping("/{id}/reading")
    @PreAuthorize("hasAuthority('UTILITY_METER_UPDATE')")
    public ResponseEntity<UtilityMeterResponse> updateMeterReading(
            @PathVariable Long id,
            @RequestBody @Valid MeterReadingUpdateRequest request) {
        return ResponseEntity.ok(utilityMeterService.updateMeterReading(id, request));
    }
}