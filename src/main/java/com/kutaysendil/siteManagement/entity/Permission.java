package com.kutaysendil.siteManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "role_claims")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission extends BaseAuditableEntity {

    @Column(nullable = false, unique = true)
    private String name;  // örn: "CREATE_USER", "VIEW_INVOICE"

    private String description;
}