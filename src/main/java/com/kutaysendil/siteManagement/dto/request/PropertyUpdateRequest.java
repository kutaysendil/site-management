package com.kutaysendil.siteManagement.dto.request;

import com.kutaysendil.siteManagement.enums.PropertyType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyUpdateRequest {
    private String block;
    private String apartmentNumber;
    private Integer floor;
    private Double grossArea;
    private Double netArea;
    private PropertyType type;
    private Long ownerId;
    private Long tenantId;
}