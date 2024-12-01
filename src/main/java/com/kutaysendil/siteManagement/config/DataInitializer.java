package com.kutaysendil.siteManagement.config;

import com.kutaysendil.siteManagement.entity.Permission;
import com.kutaysendil.siteManagement.entity.Role;
import com.kutaysendil.siteManagement.repository.PermissionRepository;
import com.kutaysendil.siteManagement.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Eğer roller yoksa oluştur
        if (roleRepository.count() == 0) {
            initializeRoles();
        }
    }

    private void initializeRoles() {
        // Temel permission'ları oluştur
        Permission viewProfile = createPermission("VIEW_PROFILE", "Profil görüntüleme yetkisi");
        Permission editProfile = createPermission("EDIT_PROFILE", "Profil düzenleme yetkisi");
        Permission viewInvoice = createPermission("VIEW_INVOICE", "Fatura görüntüleme yetkisi");
        Permission payInvoice = createPermission("PAY_INVOICE", "Fatura ödeme yetkisi");
        Permission openDoor = createPermission("OPEN_DOOR", "Kapı açma yetkisi");

        // Admin permission'ları
        Permission manageUsers = createPermission("MANAGE_USERS", "Kullanıcı yönetimi yetkisi");
        Permission manageRoles = createPermission("MANAGE_ROLES", "Rol yönetimi yetkisi");
        Permission manageInvoices = createPermission("MANAGE_INVOICES", "Fatura yönetimi yetkisi");

        // USER rolü
        Set<Permission> userPermissions = new HashSet<>();
        userPermissions.add(viewProfile);
        userPermissions.add(editProfile);
        userPermissions.add(viewInvoice);
        userPermissions.add(payInvoice);
        userPermissions.add(openDoor);

        Role userRole = Role.builder()
                .name("USER")
                .description("Standart kullanıcı rolü")
                .permissions(userPermissions)
                .build();

        // ADMIN rolü
        Set<Permission> adminPermissions = new HashSet<>(userPermissions);
        adminPermissions.add(manageUsers);
        adminPermissions.add(manageRoles);
        adminPermissions.add(manageInvoices);

        Role adminRole = Role.builder()
                .name("ADMIN")
                .description("Yönetici rolü")
                .permissions(adminPermissions)
                .build();

        // Rolleri kaydet
        roleRepository.saveAll(List.of(userRole, adminRole));
    }

    private Permission createPermission(String name, String description) {
        Permission permission = Permission.builder()
                .name(name)
                .description(description)
                .build();
        return permissionRepository.save(permission);
    }
}