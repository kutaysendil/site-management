package com.kutaysendil.siteManagement.dto.response;

import com.kutaysendil.siteManagement.enums.UtilityType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityMeterResponse {
    private Long id;
    private PropertySummaryResponse property;
    private String meterNumber;
    private UtilityType type;
    private Double lastReading;
    private Date lastReadingDate;
    private Date createdAt;
    private Date updatedAt;
}
