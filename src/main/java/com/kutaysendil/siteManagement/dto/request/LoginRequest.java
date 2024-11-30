package com.kutaysendil.siteManagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Mail gerekli")
    @Email(message = "Geçersiz mail formatı")
    private String email;

    @NotBlank(message = "Parola gerekli")
    private String password;
}
