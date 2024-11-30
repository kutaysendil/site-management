package com.kutaysendil.siteManagement.service.impl;


import com.kutaysendil.siteManagement.entity.User;
import com.kutaysendil.siteManagement.repository.UserRepository;
import com.kutaysendil.siteManagement.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Bu mail adresine sahip kullanıcı bulunamadı: " + email));

        // Aktif olmayan kullanıcıları kontrol et
        if (!user.isActive() || user.isDeleted()) {
            throw new UsernameNotFoundException("Kullanıcı aktif durumda değil");
        }

        return new CustomUserDetails(user);
    }
}