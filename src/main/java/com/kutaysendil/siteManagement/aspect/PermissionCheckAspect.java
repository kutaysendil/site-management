package com.kutaysendil.siteManagement.aspect;

import com.kutaysendil.siteManagement.annotations.RequirePermission;
import com.kutaysendil.siteManagement.exception.auth.AuthorizationException;
import com.kutaysendil.siteManagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionCheckAspect {

    private final JwtService jwtService;

    @Before("@annotation(requirePermission)")
    public void checkPermission(JoinPoint joinPoint, RequirePermission requirePermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthorizationException("Kimlik doğrulaması yapılmamış.");
        }

        boolean hasPermission = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(requirePermission.value()));

        if (!hasPermission) {
            throw new AuthorizationException("Bu işlem için yetkiniz bulunmamaktadır.");
        }
    }
}