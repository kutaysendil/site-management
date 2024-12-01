package com.kutaysendil.siteManagement.dto.request;

import com.kutaysendil.siteManagement.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateRequest {

    @NotBlank(message = "Block cannot be empty")
    private String block;

    @NotBlank(message = "Apartment number cannot be empty")
    private String apartmentNumber;

    @NotNull(message = "Floor cannot be null")
    private Integer floor;

    @NotNull(message = "Gross area cannot be null")
    @Positive(message = "Gross area must be positive")
    private Double grossArea;

    @NotNull(message = "Net area cannot be null")
    @Positive(message = "Net area must be positive")
    private Double netArea;

    @NotNull(message = "Property type cannot be null")
    private PropertyType type;

    private Long ownerId;
    private Long tenantId;
}