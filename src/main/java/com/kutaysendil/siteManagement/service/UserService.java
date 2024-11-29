package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.dto.request.UserRequest;
import com.kutaysendil.siteManagement.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    void addClaim(Long userId, String claim);
    void removeClaim(Long userId, String claim);
}