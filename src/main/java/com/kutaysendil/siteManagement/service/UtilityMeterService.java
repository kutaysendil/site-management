package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.dto.request.MeterReadingUpdateRequest;
import com.kutaysendil.siteManagement.dto.request.UtilityMeterCreateRequest;
import com.kutaysendil.siteManagement.dto.response.PropertySummaryResponse;
import com.kutaysendil.siteManagement.dto.response.UtilityMeterResponse;
import com.kutaysendil.siteManagement.entity.Property;
import com.kutaysendil.siteManagement.entity.UtilityMeter;
import com.kutaysendil.siteManagement.exception.NotFoundException;
import com.kutaysendil.siteManagement.repository.PropertyRepository;
import com.kutaysendil.siteManagement.repository.UtilityMeterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilityMeterService {

    private final UtilityMeterRepository utilityMeterRepository;
    private final PropertyRepository propertyRepository;

    public UtilityMeterResponse createMeter(UtilityMeterCreateRequest request) {
        Property property = propertyRepository.findByIdAndDeleted(request.getPropertyId(), false)
                .orElseThrow(() -> new NotFoundException("Property not found with id: " + request.getPropertyId()));

        // Check if meter already exists for this property and type
        utilityMeterRepository.findByPropertyIdAndTypeAndDeleted(request.getPropertyId(), request.getType(), false)
                .ifPresent(meter -> {
                    throw new NotFoundException("Meter already exists for this property and utility type");
                });

        UtilityMeter meter = UtilityMeter.builder()
                .property(property)
                .meterNumber(request.getMeterNumber())
                .type(request.getType())
                .lastReading(request.getLastReading())
                .lastReadingDate(new Date())
                .build();

        meter = utilityMeterRepository.save(meter);
        return convertToResponse(meter);
    }

    public Page<UtilityMeterResponse> getAllMeters(Pageable pageable) {
        Page<UtilityMeter> meters = utilityMeterRepository.findByDeleted(false, pageable);
        return meters.map(this::convertToResponse);
    }

    public List<UtilityMeterResponse> getPropertyMeters(Long propertyId) {
        List<UtilityMeter> meters = utilityMeterRepository.findByPropertyId(propertyId);
        return meters.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public UtilityMeterResponse updateMeterReading(Long id, MeterReadingUpdateRequest request) {
        UtilityMeter meter = utilityMeterRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("Meter not found with id: " + id));

        if (request.getReading() < meter.getLastReading()) {
            throw new NotFoundException("New reading cannot be less than previous reading");
        }

        meter.setLastReading(request.getReading());
        meter.setLastReadingDate(new Date());

        meter = utilityMeterRepository.save(meter);
        return convertToResponse(meter);
    }

    private UtilityMeterResponse convertToResponse(UtilityMeter meter) {
        return UtilityMeterResponse.builder()
                .id(meter.getId())
                .property(convertToPropertySummary(meter.getProperty()))
                .meterNumber(meter.getMeterNumber())
                .type(meter.getType())
                .lastReading(meter.getLastReading())
                .lastReadingDate(meter.getLastReadingDate())
                .createdAt(meter.getCreatedAt())
                .updatedAt(meter.getUpdatedAt())
                .build();
    }

    private PropertySummaryResponse convertToPropertySummary(Property property) {
        return PropertySummaryResponse.builder()
                .id(property.getId())
                .block(property.getBlock())
                .apartmentNumber(property.getApartmentNumber())
                .type(property.getType())
                .build();
    }
}
