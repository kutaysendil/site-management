package com.kutaysendil.siteManagement.aspect;


import com.kutaysendil.siteManagement.annotation.ClaimOperator;
import com.kutaysendil.siteManagement.annotation.RequiresClaim;
import com.kutaysendil.siteManagement.annotation.claims.AdminOrUserManager;
import com.kutaysendil.siteManagement.exception.auth.AuthorizationException;
import com.kutaysendil.siteManagement.security.CustomUserDetails;
import com.kutaysendil.siteManagement.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final ClaimService claimService;

    // Temel annotation işleyici
    @Before("@annotation(requiresClaim)")
    public void checkClaims(RequiresClaim requiresClaim) {
        validateClaims(requiresClaim.value(), requiresClaim.operator());
    }

    // Meta annotation işleyici
    @Before("@within(requiresClaim) && !@annotation(requiresClaim)")
    public void checkTypeAnnotation(RequiresClaim requiresClaim) {
        validateClaims(requiresClaim.value(), requiresClaim.operator());
    }

    // Composable annotation işleyici
    @Before("@annotation(adminOrUserManager)")
    public void checkComposableAnnotation(AdminOrUserManager adminOrUserManager) {
        // Her iki annotation'ın claim'lerini kontrol et
        validateClaims(new String[]{"ADMIN_ACCESS"}, ClaimOperator.OR);
        validateClaims(new String[]{"MANAGE_USERS", "ADMIN_ACCESS"}, ClaimOperator.OR);
    }

    private void validateClaims(String[] requiredClaims, ClaimOperator operator) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthorizationException("User not authenticated");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Set<String> userClaims = userDetails.getUser().getClaimNames();

        boolean hasAccess = operator == ClaimOperator.AND
                ? Arrays.stream(requiredClaims).allMatch(userClaims::contains)
                : Arrays.stream(requiredClaims).anyMatch(userClaims::contains);

        if (!hasAccess) {
            throw new AuthorizationException("Kullanıcının yetkisi yok");
        }
    }
}