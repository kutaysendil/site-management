package com.kutaysendil.siteManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoleCreateRequest {
    @NotBlank
    private String name;
    private String description;
    private Set<Long> permissionIds;
}