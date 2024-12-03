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
        if (roleRepository.count() == 0) {
            initializeRoles();
        }
    }

    private void initializeRoles() {
        // AUTH permissions
        Permission registerUser = createPermission("REGISTER_USER", "Kullanıcı kaydı yapma yetkisi");
        Permission loginUser = createPermission("LOGIN_USER", "Kullanıcı girişi yapma yetkisi");

        // PROPERTY permissions
        Permission propertyCreate = createPermission("PROPERTY_CREATE", "Mülk oluşturma yetkisi");
        Permission propertyRead = createPermission("PROPERTY_READ", "Mülk okuma yetkisi");
        Permission propertyUpdate = createPermission("PROPERTY_UPDATE", "Mülk güncelleme yetkisi");
        Permission propertyDelete = createPermission("PROPERTY_DELETE", "Mülk silme yetkisi");

        // BILL permissions
        Permission billCreate = createPermission("BILL_CREATE", "Fatura oluşturma yetkisi");
        Permission billRead = createPermission("BILL_READ", "Fatura okuma yetkisi");
        Permission billUpdate = createPermission("BILL_UPDATE", "Fatura güncelleme yetkisi");
        Permission billDetailCreate = createPermission("BILL_DETAIL_CREATE", "Fatura detayı oluşturma yetkisi");
        Permission billDetailRead = createPermission("BILL_DETAIL_READ", "Fatura detayı okuma yetkisi");

        // ROLE permissions
        Permission roleCreate = createPermission("ROLE_CREATE", "Rol oluşturma yetkisi");
        Permission roleRead = createPermission("ROLE_READ", "Rol okuma yetkisi");
        Permission roleUpdate = createPermission("ROLE_UPDATE", "Rol güncelleme yetkisi");
        Permission roleDelete = createPermission("ROLE_DELETE", "Rol silme yetkisi");

        // PERMISSION permissions
        Permission permissionRead = createPermission("PERMISSION_READ", "Yetki okuma yetkisi");
        Permission permissionAssign = createPermission("PERMISSION_ASSIGN", "Yetki atama yetkisi");

        // UTILITY METER permissions
        Permission utilityMeterCreate = createPermission("UTILITY_METER_CREATE", "Sayaç oluşturma yetkisi");
        Permission utilityMeterRead = createPermission("UTILITY_METER_READ", "Sayaç okuma yetkisi");
        Permission utilityMeterUpdate = createPermission("UTILITY_METER_UPDATE", "Sayaç güncelleme yetkisi");

        // USER rolü için temel yetkiler
        Set<Permission> userPermissions = new HashSet<>();
        userPermissions.add(loginUser);
        userPermissions.add(registerUser);
        userPermissions.add(propertyRead);
        userPermissions.add(billRead);
        userPermissions.add(billDetailRead);
        userPermissions.add(utilityMeterRead);

        // ADMIN rolü için tüm yetkiler
        Set<Permission> adminPermissions = new HashSet<>();
        adminPermissions.addAll(permissionRepository.findAll()); // Tüm permission'ları ekle

        // Rolleri oluştur
        Role userRole = Role.builder()
                .name("USER")
                .description("Standart kullanıcı rolü")
                .permissions(userPermissions)
                .build();

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