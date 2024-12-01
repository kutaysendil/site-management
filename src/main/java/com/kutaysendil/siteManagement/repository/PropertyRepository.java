package com.kutaysendil.siteManagement.repository;

import com.kutaysendil.siteManagement.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyRepository, Long> {
    Property save(Property property);

    Page<Property> findByDeleted(boolean deleted, Pageable pageable);

    Optional<Property> findByIdAndDeleted(Long id, boolean deleted);

    List<Property> findByOwnerId(Long ownerId);

    List<Property> findByTenantId(Long tenantId);

    @Query("SELECT p FROM Property p WHERE p.block = :block AND p.apartmentNumber = :apartmentNumber AND p.deleted = false")
    Optional<Property> findByBlockAndApartmentNumber(String block, String apartmentNumber);
}