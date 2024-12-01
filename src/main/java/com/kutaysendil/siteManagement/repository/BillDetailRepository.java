package com.kutaysendil.siteManagement.repository;

import com.kutaysendil.siteManagement.entity.BillDetail;
import com.kutaysendil.siteManagement.enums.UtilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    List<BillDetail> findByBillId(Long billId);

    List<BillDetail> findByPropertyId(Long propertyId);

    List<BillDetail> findByBillIdAndIsPaid(Long billId, boolean isPaid);

    @Query("SELECT bd FROM BillDetail bd WHERE bd.property.id = :propertyId AND bd.bill.type = :type AND bd.bill.startDate >= :startDate")
    List<BillDetail> findPropertyBillsByTypeAndDate(Long propertyId, UtilityType type, Date startDate);
}