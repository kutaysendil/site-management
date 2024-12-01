package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.dto.request.BillCreateRequest;
import com.kutaysendil.siteManagement.dto.request.BillDetailCreateRequest;
import com.kutaysendil.siteManagement.dto.request.BillUpdateRequest;
import com.kutaysendil.siteManagement.dto.response.BillDetailResponse;
import com.kutaysendil.siteManagement.dto.response.BillResponse;
import com.kutaysendil.siteManagement.dto.response.PropertySummaryResponse;
import com.kutaysendil.siteManagement.entity.Bill;
import com.kutaysendil.siteManagement.entity.BillDetail;
import com.kutaysendil.siteManagement.entity.Property;
import com.kutaysendil.siteManagement.exception.NotFoundException;
import com.kutaysendil.siteManagement.repository.BillDetailRepository;
import com.kutaysendil.siteManagement.repository.BillRepository;
import com.kutaysendil.siteManagement.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BillService {

    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final PropertyRepository propertyRepository;

    public BillResponse createBill(BillCreateRequest request) {
        Bill bill = Bill.builder()
                .type(request.getType())
                .totalAmount(request.getTotalAmount())
                .totalConsumption(request.getTotalConsumption())
                .unitPrice(request.getUnitPrice())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        bill = billRepository.save(bill);
        return convertToResponse(bill);
    }

    public BillDetailResponse addBillDetail(Long billId, BillDetailCreateRequest request) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException("Bill not found with id: " + billId));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new NotFoundException("Property not found with id: " + request.getPropertyId()));

        BillDetail billDetail = BillDetail.builder()
                .bill(bill)
                .property(property)
                .consumption(request.getConsumption())
                .amount(bill.calculatePropertyAmount(request.getConsumption()))
                .isPaid(false)
                .build();

        billDetail = billDetailRepository.save(billDetail);
        return convertToBillDetailResponse(billDetail);
    }

    public BillResponse getBill(Long id) {
        Bill bill = billRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("Bill not found with id: " + id));
        return convertToResponse(bill);
    }

    public List<BillDetailResponse> getBillDetails(Long billId) {
        Bill bill = billRepository.findByIdAndDeleted(billId, false)
                .orElseThrow(() -> new NotFoundException("Bill not found with id: " + billId));

        return bill.getBillDetails().stream()
                .map(this::convertToBillDetailResponse)
                .collect(Collectors.toList());
    }

    public Page<BillResponse> getAllBills(Pageable pageable) {
        Page<Bill> bills = billRepository.findByDeleted(false, pageable);
        return bills.map(this::convertToResponse);
    }

    public BillResponse updateBill(Long id, BillUpdateRequest request) {
        Bill bill = billRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new NotFoundException("Bill not found with id: " + id));

        // Null check ile güncelleme
        if (request.getType() != null) {
            bill.setType(request.getType());
        }
        if (request.getTotalAmount() != null) {
            bill.setTotalAmount(request.getTotalAmount());
        }
        if (request.getTotalConsumption() != null) {
            bill.setTotalConsumption(request.getTotalConsumption());
        }
        if (request.getUnitPrice() != null) {
            bill.setUnitPrice(request.getUnitPrice());

            // Sadece henüz detay eklenmemiş faturada birim fiyat değişebilir
            if (!bill.getBillDetails().isEmpty()) {
                throw new NotFoundException("Cannot update unit price after bill details have been added");
            }
        }
        if (request.getStartDate() != null) {
            bill.setStartDate(request.getStartDate());
        }


        bill = billRepository.save(bill);
        return convertToResponse(bill);
    }

    private BillResponse convertToResponse(Bill bill) {
        return BillResponse.builder()
                .id(bill.getId())
                .type(bill.getType())
                .totalAmount(bill.getTotalAmount())
                .totalConsumption(bill.getTotalConsumption())
                .unitPrice(bill.getUnitPrice())
                .startDate(bill.getStartDate())
                .endDate(bill.getEndDate())
                .details(bill.getBillDetails().stream()
                        .map(this::convertToBillDetailResponse)
                        .collect(Collectors.toList()))
                .createdAt(bill.getCreatedAt())
                .updatedAt(bill.getUpdatedAt())
                .build();
    }

    private BillDetailResponse convertToBillDetailResponse(BillDetail detail) {
        return BillDetailResponse.builder()
                .id(detail.getId())
                .property(convertToPropertySummary(detail.getProperty()))
                .consumption(detail.getConsumption())
                .amount(detail.getAmount())
                .isPaid(detail.getIsPaid())
                .build();
    }

    private PropertySummaryResponse convertToPropertySummary(Property property) {
        return PropertySummaryResponse.builder()
                .id(property.getId())
                .block(property.getBlock())
                .apartmentNumber(property.getApartmentNumber())
                .type(property.getType())
                .build();
    }
}