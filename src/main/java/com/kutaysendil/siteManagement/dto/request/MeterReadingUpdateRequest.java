package com.kutaysendil.siteManagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingUpdateRequest {
    @NotNull(message = "Reading cannot be null")
    @PositiveOrZero(message = "Reading must be positive or zero")
    private Double reading;
}