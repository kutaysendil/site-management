package com.kutaysendil.siteManagement.dto.request;

import com.kutaysendil.siteManagement.enums.UtilityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillCreateRequest {
    @NotNull(message = "Utility type cannot be null")
    private UtilityType type;

    @NotNull(message = "Total amount cannot be null")
    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

    @NotNull(message = "Total consumption cannot be null")
    @Positive(message = "Total consumption must be positive")
    private Double totalConsumption;

    @NotNull(message = "Unit price cannot be null")
    @Positive(message = "Unit price must be positive")
    private Double unitPrice;

    @NotNull(message = "Start date cannot be null")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    private Date endDate;
}
