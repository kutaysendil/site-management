package com.kutaysendil.siteManagement.dto.response;

import com.kutaysendil.siteManagement.enums.PropertyType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertySummaryResponse {
    private Long id;
    private String block;
    private String apartmentNumber;
    private PropertyType type;
}