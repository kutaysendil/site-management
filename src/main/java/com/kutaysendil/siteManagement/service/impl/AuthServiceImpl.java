package com.kutaysendil.siteManagement.service.impl;


import com.kutaysendil.siteManagement.dto.request.LoginRequest;
import com.kutaysendil.siteManagement.dto.request.RegisterRequest;
import com.kutaysendil.siteManagement.dto.response.AuthResponse;
import com.kutaysendil.siteManagement.entity.Claim;
import com.kutaysendil.siteManagement.entity.User;
import com.kutaysendil.siteManagement.entity.UserClaim;
import com.kutaysendil.siteManagement.exception.auth.EmailAlreadyExistsException;
import com.kutaysendil.siteManagement.exception.auth.InvalidTokenException;
import com.kutaysendil.siteManagement.exception.user.UserNotFoundException;
import com.kutaysendil.siteManagement.repository.ClaimRepository;
import com.kutaysendil.siteManagement.repository.UserRepository;
import com.kutaysendil.siteManagement.security.CustomUserDetails;
import com.kutaysendil.siteManagement.security.JwtService;
import com.kutaysendil.siteManagement.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClaimRepository claimRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Mail adresi zaten kayıtlı");
        }

        // Kullanıcıyı oluştur
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .surname(request.getSurname())
                .apartmentNumber(request.getApartmentNumber())
                .phoneNumber(request.getPhoneNumber())
                .userClaims(new HashSet<>()) // boş set ile başlat
                .active(true)
                .build();

        var savedUser = userRepository.save(user);

        // Varsayılan ROLE_USER claim'ini ekle
        Claim defaultClaim = claimRepository.findByName("ROLE_USER")
                .orElseGet(() -> claimRepository.save(Claim.builder()
                        .name("ROLE_USER")
                        .description("Default user role")
                        .active(true)
                        .build()));

        UserClaim userClaim = UserClaim.builder()
                .user(savedUser)
                .claim(defaultClaim)
                .build();

        savedUser.getUserClaims().add(userClaim);
        savedUser = userRepository.save(savedUser);

        var userDetails = new CustomUserDetails(savedUser);

        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .claims(new ArrayList<>(savedUser.getClaimNames()))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        var userDetails = new CustomUserDetails(user);

        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshToken(refreshToken)
                .claims(new ArrayList<>(user.getClaimNames()))
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

            var userDetails = new CustomUserDetails(user);

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);

                return AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .claims(new ArrayList<>(user.getClaimNames()))
                        .build();
            }
        }
        throw new InvalidTokenException("Geçersiz token");
    }
}