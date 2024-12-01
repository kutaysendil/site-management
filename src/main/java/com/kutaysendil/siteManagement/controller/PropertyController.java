package com.kutaysendil.siteManagement.controller;


import com.kutaysendil.siteManagement.dto.request.PropertyCreateRequest;
import com.kutaysendil.siteManagement.dto.request.PropertyUpdateRequest;
import com.kutaysendil.siteManagement.dto.response.PropertyResponse;
import com.kutaysendil.siteManagement.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasAuthority('PROPERTY_CREATE')")
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody @Valid PropertyCreateRequest request) {
        return ResponseEntity.ok(propertyService.createProperty(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PROPERTY_READ')")
    public ResponseEntity<Page<PropertyResponse>> getAllProperties(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAllProperties(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PROPERTY_READ')")
    public ResponseEntity<PropertyResponse> getProperty(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getProperty(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PROPERTY_UPDATE')")
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable Long id,
                                                           @RequestBody @Valid PropertyUpdateRequest request) {
        return ResponseEntity.ok(propertyService.updateProperty(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PROPERTY_DELETE')")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}