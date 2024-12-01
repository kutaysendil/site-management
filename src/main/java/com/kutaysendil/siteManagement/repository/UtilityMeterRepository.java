package com.kutaysendil.siteManagement.repository;

import com.kutaysendil.siteManagement.entity.UtilityMeter;
import com.kutaysendil.siteManagement.enums.UtilityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilityMeterRepository extends JpaRepository<UtilityMeter, Long> {
    List<UtilityMeter> findByPropertyId(Long propertyId);

    List<UtilityMeter> findByType(UtilityType type);

    Page<UtilityMeter> findByTypeAndLastReadingDateBetween(UtilityType type, Date startDate, Date endDate, Pageable pageable);

    Page<UtilityMeter> findByLastReadingDateBetween(Date startDate, Date endDate, Pageable pageable);

    Optional<UtilityMeter> findByPropertyIdAndType(Long propertyId, UtilityType type);

    @Query("SELECT um FROM UtilityMeter um WHERE um.property.id = :propertyId AND um.type = :type AND um.lastReadingDate >= :date")
    List<UtilityMeter> findRecentReadingsByPropertyAndType(Long propertyId, UtilityType type, Date date);

    Page<UtilityMeter> findByDeleted(boolean deleted, Pageable pageable);

    Optional<UtilityMeter> findByIdAndDeleted(Long id, boolean deleted);


    Optional<UtilityMeter> findByPropertyIdAndTypeAndDeleted(
            Long propertyId,
            UtilityType type,
            boolean deleted
    );
}