package com.kutaysendil.siteManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Set<String> claims;
    private String apartmentNumber;
    private String phoneNumber;
    private boolean active;
}
