package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.dto.request.PropertyCreateRequest;
import com.kutaysendil.siteManagement.dto.request.PropertyUpdateRequest;
import com.kutaysendil.siteManagement.dto.response.PropertyResponse;
import com.kutaysendil.siteManagement.dto.response.UserSummaryResponse;
import com.kutaysendil.siteManagement.dto.response.UtilityMeterSummaryResponse;
import com.kutaysendil.siteManagement.entity.Property;
import com.kutaysendil.siteManagement.entity.User;
import com.kutaysendil.siteManagement.entity.UtilityMeter;
import com.kutaysendil.siteManagement.exception.NotFoundException;
import com.kutaysendil.siteManagement.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Page<PropertyResponse> getAllProperties(Pageable pageable) {
        Page<Property> properties = propertyRepository.findByDeleted(false, pageable);
        return properties.map(this::convertToResponse);
    }

    public PropertyResponse getProperty(Long id) {
        Property property = propertyRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("Property not found with id: " + id));
        return convertToResponse(property);
    }

    public PropertyResponse createProperty(PropertyCreateRequest request) {
        Property property = Property.builder()
                .block(request.getBlock())
                .apartmentNumber(request.getApartmentNumber())
                .floor(request.getFloor())
                .grossArea(request.getGrossArea())
                .netArea(request.getNetArea())
                .type(request.getType())
                .build();

        property = propertyRepository.save(property);
        return convertToResponse(property);
    }

    public PropertyResponse updateProperty(Long id, PropertyUpdateRequest request) {
        Property property = propertyRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("Property not found with id: " + id));

        if (request.getBlock() != null) {
            property.setBlock(request.getBlock());
        }
        if (request.getApartmentNumber() != null) {
            property.setApartmentNumber(request.getApartmentNumber());
        }
        if (request.getFloor() != null) {
            property.setFloor(request.getFloor());
        }
        if (request.getGrossArea() != null) {
            property.setGrossArea(request.getGrossArea());
        }
        if (request.getNetArea() != null) {
            property.setNetArea(request.getNetArea());
        }
        if (request.getType() != null) {
            property.setType(request.getType());
        }

        property = propertyRepository.save(property);
        return convertToResponse(property);
    }

    public void deleteProperty(Long id) {
        Property property = propertyRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("Property not found with id: " + id) {
                });
        property.setDeleted(true);
        propertyRepository.save(property);
    }

    private PropertyResponse convertToResponse(Property property) {
        return PropertyResponse.builder()
                .id(property.getId())
                .block(property.getBlock())
                .apartmentNumber(property.getApartmentNumber())
                .floor(property.getFloor())
                .grossArea(property.getGrossArea())
                .netArea(property.getNetArea())
                .type(property.getType())
                .owner(property.getOwner() != null ? convertToUserSummary(property.getOwner()) : null)
                .tenant(property.getTenant() != null ? convertToUserSummary(property.getTenant()) : null)
                .meters(property.getUtilityMeters().stream()
                        .map(this::convertToMeterSummary)
                        .collect(Collectors.toList()))
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }

    private UserSummaryResponse convertToUserSummary(User user) {
        return UserSummaryResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

    private UtilityMeterSummaryResponse convertToMeterSummary(UtilityMeter meter) {
        return UtilityMeterSummaryResponse.builder()
                .id(meter.getId())
                .meterNumber(meter.getMeterNumber())
                .type(meter.getType())
                .lastReading(meter.getLastReading())
                .build();
    }
}