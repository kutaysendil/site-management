package com.kutaysendil.siteManagement.controller;

import com.kutaysendil.siteManagement.dto.request.BillCreateRequest;
import com.kutaysendil.siteManagement.dto.request.BillDetailCreateRequest;
import com.kutaysendil.siteManagement.dto.request.BillUpdateRequest;
import com.kutaysendil.siteManagement.dto.response.BillDetailResponse;
import com.kutaysendil.siteManagement.dto.response.BillResponse;
import com.kutaysendil.siteManagement.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    @PreAuthorize("hasAuthority('BILL_CREATE')")
    public ResponseEntity<BillResponse> createBill(@RequestBody @Valid BillCreateRequest request) {
        return ResponseEntity.ok(billService.createBill(request));
    }

    @PostMapping("/{billId}/details")
    @PreAuthorize("hasAuthority('BILL_DETAIL_CREATE')")
    public ResponseEntity<BillDetailResponse> addBillDetail(@PathVariable Long billId,
                                                            @RequestBody @Valid BillDetailCreateRequest request) {
        return ResponseEntity.ok(billService.addBillDetail(billId, request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BILL_READ')")
    public ResponseEntity<Page<BillResponse>> getAllBills(Pageable pageable) {
        return ResponseEntity.ok(billService.getAllBills(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BILL_READ')")
    public ResponseEntity<BillResponse> getBill(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBill(id));
    }

    @GetMapping("/{billId}/details")
    @PreAuthorize("hasAuthority('BILL_DETAIL_READ')")
    public ResponseEntity<List<BillDetailResponse>> getBillDetails(@PathVariable Long billId) {
        return ResponseEntity.ok(billService.getBillDetails(billId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BILL_UPDATE')")
    public ResponseEntity<BillResponse> updateBill(@PathVariable Long id,
                                                   @RequestBody @Valid BillUpdateRequest request) {
        return ResponseEntity.ok(billService.updateBill(id, request));
    }
}