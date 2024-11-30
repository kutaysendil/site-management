package com.kutaysendil.siteManagement.service.impl;

import com.kutaysendil.siteManagement.entity.Claim;
import com.kutaysendil.siteManagement.entity.User;
import com.kutaysendil.siteManagement.entity.UserClaim;
import com.kutaysendil.siteManagement.exception.claim.ClaimAlreadyExistsException;
import com.kutaysendil.siteManagement.exception.claim.ClaimNotFoundException;
import com.kutaysendil.siteManagement.exception.user.UserNotFoundException;
import com.kutaysendil.siteManagement.repository.ClaimRepository;
import com.kutaysendil.siteManagement.repository.UserClaimRepository;
import com.kutaysendil.siteManagement.repository.UserRepository;
import com.kutaysendil.siteManagement.service.ClaimService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final UserClaimRepository userClaimRepository;

    @Override
    public void addClaimToUser(Long userId, String claimName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        Claim claim = claimRepository.findByName(claimName)
                .orElseThrow(() -> new ClaimNotFoundException("Yetki bulunamadı: " + claimName));

        // Zaten var mı kontrol et
        boolean alreadyHasClaim = user.getUserClaims().stream()
                .anyMatch(uc -> uc.getClaim().getName().equals(claimName));

        if (alreadyHasClaim) {
            throw new ClaimAlreadyExistsException("Kullanıcıda bu yetki zaten var");
        }

        UserClaim userClaim = UserClaim.builder()
                .user(user)
                .claim(claim)
                .build();

        user.getUserClaims().add(userClaim);
        userRepository.save(user);
    }

    @Override
    public void removeClaimFromUser(Long userId, String claimName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        Claim claim = claimRepository.findByName(claimName)
                .orElseThrow(() -> new ClaimNotFoundException("Yetki bulunamadı : " + claimName));

        user.getUserClaims().removeIf(uc -> uc.getClaim().getName().equals(claimName));
        userRepository.save(user);
    }

    @Override
    public Set<String> getUserClaims(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));

        return user.getClaimNames();
    }

    @Override
    public Claim createClaim(String name, String description) {
        if (claimRepository.existsByName(name)) {
            throw new ClaimAlreadyExistsException("Bu yetki bu adla zaten var : " + name);
        }

        Claim claim = Claim.builder()
                .name(name)
                .description(description)
                .active(true)
                .build();

        return claimRepository.save(claim);
    }

    @Override
    public void deleteClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Yetki bulunamadı"));

        // Soft delete
        claim.setDeleted(true);
        claim.setActive(false);
        claimRepository.save(claim);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
}