package com.kutaysendil.siteManagement.dto.response;

import com.kutaysendil.siteManagement.enums.PropertyType;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponse {
    private Long id;
    private String block;
    private String apartmentNumber;
    private Integer floor;
    private Double grossArea;
    private Double netArea;
    private PropertyType type;
    private UserSummaryResponse owner;
    private UserSummaryResponse tenant;
    private List<UtilityMeterSummaryResponse> meters;
    private Date createdAt;
    private Date updatedAt;
}