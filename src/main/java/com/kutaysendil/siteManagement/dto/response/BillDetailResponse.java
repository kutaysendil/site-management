package com.kutaysendil.siteManagement.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailResponse {
    private Long id;
    private PropertySummaryResponse property;
    private Double consumption;
    private Double amount;
    private Boolean isPaid;
}