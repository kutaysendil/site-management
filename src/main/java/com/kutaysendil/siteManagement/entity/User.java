package com.kutaysendil.siteManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_claims",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> claims = new HashSet<>();

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean active = true;
}