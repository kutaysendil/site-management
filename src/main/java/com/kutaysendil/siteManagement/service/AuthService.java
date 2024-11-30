package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.dto.request.LoginRequest;
import com.kutaysendil.siteManagement.dto.request.RegisterRequest;
import com.kutaysendil.siteManagement.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
}