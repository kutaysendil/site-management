package com.kutaysendil.siteManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String name;
    private String surname;
    private String apartmentNumber;
    private String phoneNumber;
    private Set<String> roles;        // claims yerine roles ve permissions
    private Set<String> permissions;  // ayrı ayrı dönüyoruz
}