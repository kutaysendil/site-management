package com.kutaysendil.siteManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private List<String> claims;
    private String email;
    private String name;
    private String surname;
    private String apartmentNumber;
    private String phoneNumber;
}