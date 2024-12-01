package com.kutaysendil.siteManagement.dto.response;

import com.kutaysendil.siteManagement.enums.UtilityType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityMeterSummaryResponse {
    private Long id;
    private String meterNumber;
    private UtilityType type;
    private Double lastReading;
}