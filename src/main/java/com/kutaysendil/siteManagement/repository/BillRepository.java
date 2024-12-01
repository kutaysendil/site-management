package com.kutaysendil.siteManagement.repository;

import com.kutaysendil.siteManagement.entity.Bill;
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
public interface BillRepository extends JpaRepository<Bill, Long> {
    Page<Bill> findByDeleted(boolean deleted, Pageable pageable);

    List<Bill> findByTypeAndStartDateBetween(UtilityType type, Date startDate, Date endDate);

    Optional<Bill> findByIdAndDeleted(Long id, boolean deleted);

    @Query("SELECT b FROM Bill b WHERE b.type = :type AND b.startDate >= :date AND b.deleted = false")
    List<Bill> findRecentBillsByType(UtilityType type, Date date);
}