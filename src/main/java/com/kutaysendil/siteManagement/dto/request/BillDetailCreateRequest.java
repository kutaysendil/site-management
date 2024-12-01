package com.kutaysendil.siteManagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailCreateRequest {
    @NotNull(message = "Property ID cannot be null")
    private Long propertyId;

    @NotNull(message = "Consumption cannot be null")
    @PositiveOrZero(message = "Consumption must be positive or zero")
    private Double consumption;
}