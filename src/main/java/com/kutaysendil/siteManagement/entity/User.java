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

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean active = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default // Bu önemli, Builder kullanırken default değer için
    private Set<UserRole> roles = new HashSet<>(); // Null yerine boş set
}