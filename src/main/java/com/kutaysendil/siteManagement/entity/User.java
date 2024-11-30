package com.kutaysendil.siteManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseAuditableEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserClaim> userClaims = new HashSet<>();

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean active = true;

    // Helper method to get claim names
    @Transient
    public Set<String> getClaimNames() {
        return userClaims.stream()
                .map(uc -> uc.getClaim().getName())
                .collect(Collectors.toSet());
    }
}