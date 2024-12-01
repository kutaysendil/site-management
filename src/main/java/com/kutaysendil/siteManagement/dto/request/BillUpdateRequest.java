package com.kutaysendil.siteManagement.dto.request;

import com.kutaysendil.siteManagement.enums.UtilityType;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillUpdateRequest {

    private UtilityType type;

    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

    @Positive(message = "Total consumption must be positive")
    private Double totalConsumption;

    @Positive(message = "Unit price must be positive")
    private Double unitPrice;

    private Date startDate;
}