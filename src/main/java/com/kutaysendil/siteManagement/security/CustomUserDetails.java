package com.kutaysendil.siteManagement.security;


import com.kutaysendil.siteManagement.entity.Permission;
import com.kutaysendil.siteManagement.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(userRole -> {
            // Rolleri ekle
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getName()));

            // Rollere ait permission'ları ekle
            userRole.getRole().getPermissions().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            });
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public Set<String> getRoles() {
        return user.getRoles() != null
                ? user.getRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet())
                : new HashSet<>();
    }

    public Set<String> getPermissions() {
        return user.getRoles() != null
                ? user.getRoles().stream()
                .flatMap(userRole -> userRole.getRole().getPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toSet())
                : new HashSet<>();
    }
}