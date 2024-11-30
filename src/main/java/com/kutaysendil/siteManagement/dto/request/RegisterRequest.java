package com.kutaysendil.siteManagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Mail gerekli")
    @Email(message = "Geçersiz mail formatı")
    private String email;

    @NotBlank(message = "Parola gerekli")
    @Size(min = 6, message = "Parola minimum 6 karakter içermeli")
    private String password;

    @NotBlank(message = "Ad gerekli")
    private String name;

    @NotBlank(message = "Soyadı gerekli")
    private String surname;

    @NotBlank(message = "Arsa numarası gerekli")
    private String apartmentNumber;

    @NotBlank(message = "Telefon numarası gerekli")
    private String phoneNumber;
}