package com.kutaysendil.siteManagement.entity;

import com.kutaysendil.siteManagement.enums.UtilityType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "utility_meters")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityMeter extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private String meterNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UtilityType type;

    @Column(nullable = false)
    private Double lastReading;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReadingDate;
}