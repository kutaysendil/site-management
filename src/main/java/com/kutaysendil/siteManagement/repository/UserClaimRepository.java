package com.kutaysendil.siteManagement.repository;


import com.kutaysendil.siteManagement.entity.UserClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClaimRepository extends JpaRepository<UserClaim, Long> {
    List<UserClaim> findByUserId(Long userId);
    void deleteByUserIdAndClaimId(Long userId, Long claimId);
}