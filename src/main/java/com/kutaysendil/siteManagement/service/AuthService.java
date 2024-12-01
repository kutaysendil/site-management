package com.kutaysendil.siteManagement.service;


import com.kutaysendil.siteManagement.dto.request.LoginRequest;
import com.kutaysendil.siteManagement.dto.request.RegisterRequest;
import com.kutaysendil.siteManagement.dto.response.AuthResponse;
import com.kutaysendil.siteManagement.entity.Role;
import com.kutaysendil.siteManagement.entity.User;
import com.kutaysendil.siteManagement.entity.UserRole;
import com.kutaysendil.siteManagement.exception.auth.EmailAlreadyExistsException;
import com.kutaysendil.siteManagement.exception.auth.InvalidTokenException;
import com.kutaysendil.siteManagement.exception.user.UserNotFoundException;
import com.kutaysendil.siteManagement.repository.RoleRepository;
import com.kutaysendil.siteManagement.repository.UserRepository;
import com.kutaysendil.siteManagement.repository.UserRoleRepository;
import com.kutaysendil.siteManagement.security.CustomUserDetails;
import com.kutaysendil.siteManagement.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

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
                 .phoneNumber(request.getPhoneNumber())
                .active(true)
                .roles(new HashSet<>()) // Boş set ile başlat
                .build();

        var savedUser = userRepository.save(user);

        // Varsayılan USER rolünü ekle
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Varsayılan rol bulunamadı"));

        UserRole userRoleEntity = UserRole.builder()
                .user(savedUser)
                .role(userRole)
                .build();

        savedUser.getRoles().add(userRoleEntity); // User'a rolü ekle
        savedUser = userRepository.save(savedUser);

        var userDetails = new CustomUserDetails(savedUser);
        var accessToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildAuthResponse(savedUser, accessToken, refreshToken);
    }

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

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

            var userDetails = new CustomUserDetails(user);

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                return buildAuthResponse(user, accessToken, refreshToken);
            }
        }
        throw new InvalidTokenException("Geçersiz token");
    }

    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        var userDetails = new CustomUserDetails(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                 .phoneNumber(user.getPhoneNumber())
                .roles(userDetails.getRoles())           // Rolleri ekle
                .permissions(userDetails.getPermissions()) // Permission'ları ekle
                .build();
    }
}