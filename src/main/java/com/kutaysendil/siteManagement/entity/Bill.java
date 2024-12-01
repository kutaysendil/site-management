package com.kutaysendil.siteManagement.entity;

import com.kutaysendil.siteManagement.enums.UtilityType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "bills")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill extends BaseAuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UtilityType type;

    @Column(nullable = false)
    private Double totalAmount; // Toplam fatura tutarı

    @Column(nullable = false)
    private Double totalConsumption; // Toplam tüketim miktarı

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(nullable = false)
    private Double unitPrice; // Admin tarafından girilecek birim fiyat

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<BillDetail> billDetails = new HashSet<>();

    // Her bir daireye düşen tutarı hesaplayan yardımcı metod
    public Double calculatePropertyAmount(Double propertyConsumption) {
        return propertyConsumption * this.unitPrice;
    }
}