package com.kutaysendil.siteManagement.dto.response;

import com.kutaysendil.siteManagement.enums.UtilityType;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private Long id;
    private UtilityType type;
    private Double totalAmount;
    private Double totalConsumption;
    private Double unitPrice;
    private Date startDate;
    private Date endDate;
    private List<BillDetailResponse> details;
    private Date createdAt;
    private Date updatedAt;
}