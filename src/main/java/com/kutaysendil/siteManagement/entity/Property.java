package com.kutaysendil.siteManagement.entity;

import com.kutaysendil.siteManagement.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "properties")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property extends BaseAuditableEntity {

    @Column(nullable = false)
    private String block;

    @Column(nullable = false)
    private String apartmentNumber;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Double grossArea;

    @Column(nullable = false)
    private Double netArea;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private User tenant;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<UtilityMeter> utilityMeters = new HashSet<>();
}