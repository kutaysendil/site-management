package com.kutaysendil.siteManagement.exception.role;

import com.kutaysendil.siteManagement.exception.BaseException;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(String message) {
        super("ROLE_001", message);
    }
}
