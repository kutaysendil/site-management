package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.entity.Claim;

import java.util.List;
import java.util.Set;

public interface ClaimService {
    void addClaimToUser(Long userId, String claimName);
    void removeClaimFromUser(Long userId, String claimName);
    Set<String> getUserClaims(Long userId);
    Claim createClaim(String name, String description);
    void deleteClaim(Long claimId);
    List<Claim> getAllClaims();
}