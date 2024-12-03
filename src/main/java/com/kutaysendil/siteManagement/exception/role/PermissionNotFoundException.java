package com.kutaysendil.siteManagement.exception.role;

import com.kutaysendil.siteManagement.exception.BaseException;

public class PermissionNotFoundException extends BaseException {
    public PermissionNotFoundException(String message) {
        super("PERM_001", message);
    }
}