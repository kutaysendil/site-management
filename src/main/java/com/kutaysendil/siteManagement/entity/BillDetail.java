package com.kutaysendil.siteManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "bill_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private Double consumption; // Dairenin tüketim miktarı (m3)

    @Column(nullable = false)
    private Double amount; // Dairenin ödemesi gereken tutar

    @Column(nullable = false)
    private Boolean isPaid = false;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;
}
