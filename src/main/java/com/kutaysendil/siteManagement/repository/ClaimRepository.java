package com.kutaysendil.siteManagement.repository;


import com.kutaysendil.siteManagement.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Optional<Claim> findByName(String name);
    boolean existsByName(String name);
}