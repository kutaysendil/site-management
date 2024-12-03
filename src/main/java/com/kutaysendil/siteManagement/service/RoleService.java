package com.kutaysendil.siteManagement.service;

import com.kutaysendil.siteManagement.dto.request.RoleCreateRequest;
import com.kutaysendil.siteManagement.dto.request.RoleUpdateRequest;
import com.kutaysendil.siteManagement.dto.response.PermissionResponse;
import com.kutaysendil.siteManagement.dto.response.RoleResponse;
import com.kutaysendil.siteManagement.entity.Permission;
import com.kutaysendil.siteManagement.entity.Role;
import com.kutaysendil.siteManagement.exception.role.PermissionNotFoundException;
import com.kutaysendil.siteManagement.exception.role.RoleAlreadyExistsException;
import com.kutaysendil.siteManagement.exception.role.RoleNotFoundException;
import com.kutaysendil.siteManagement.repository.PermissionRepository;
import com.kutaysendil.siteManagement.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    public RoleResponse createRole(RoleCreateRequest request) {
        // İsim kontrolü
        if (roleRepository.existsByName(request.getName())) {
            throw new RoleAlreadyExistsException("Bu rol adı zaten kullanılıyor");
        }

        // Role oluştur
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(new HashSet<>())
                .build();

        // Permission'ları ekle
        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            Set<Permission> permissions = request.getPermissionIds().stream()
                    .map(id -> permissionRepository.findById(id)
                            .orElseThrow(() -> new PermissionNotFoundException("Permission bulunamadı: " + id)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }

        Role savedRole = roleRepository.save(role);
        return mapToRoleResponse(savedRole);
    }


    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToRoleResponse)
                .collect(Collectors.toList());
    }


    public RoleResponse getRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Rol bulunamadı: " + id));
        return mapToRoleResponse(role);
    }

    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Rol bulunamadı: " + id));

        // İsim değişiyorsa kontrol et
        if (!role.getName().equals(request.getName()) &&
                roleRepository.existsByName(request.getName())) {
            throw new RoleAlreadyExistsException("Bu rol adı zaten kullanılıyor");
        }

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Role updatedRole = roleRepository.save(role);
        return mapToRoleResponse(updatedRole);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Rol bulunamadı: " + id));

        // Sistemdeki son rol silinmeye çalışılıyorsa engelle
        if (roleRepository.count() == 1) {
            throw new IllegalStateException("Sistemdeki son rol silinemez");
        }

        roleRepository.delete(role);
    }

    public RoleResponse assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Rol bulunamadı: " + roleId));

        Set<Permission> permissions = permissionIds.stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new PermissionNotFoundException("Permission bulunamadı: " + id)))
                .collect(Collectors.toSet());

        role.setPermissions(permissions);
        Role updatedRole = roleRepository.save(role);
        return mapToRoleResponse(updatedRole);
    }

    private RoleResponse mapToRoleResponse(Role role) {
        Set<PermissionResponse> permissionResponses = role.getPermissions().stream()
                .map(permission -> PermissionResponse.builder()
                        .id(permission.getId())
                        .name(permission.getName())
                        .description(permission.getDescription())
                        .build())
                .collect(Collectors.toSet());

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(permissionResponses)
                .build();
    }
}
