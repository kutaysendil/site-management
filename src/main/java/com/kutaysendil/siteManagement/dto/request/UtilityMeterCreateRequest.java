package com.kutaysendil.siteManagement.dto.request;

import com.kutaysendil.siteManagement.enums.UtilityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityMeterCreateRequest {
    @NotNull(message = "Property ID cannot be null")
    private Long propertyId;

    @NotBlank(message = "Meter number cannot be empty")
    private String meterNumber;

    @NotNull(message = "Utility type cannot be null")
    private UtilityType type;

    @NotNull(message = "Last reading cannot be null")
    @PositiveOrZero(message = "Last reading must be positive or zero")
    private Double lastReading;
}