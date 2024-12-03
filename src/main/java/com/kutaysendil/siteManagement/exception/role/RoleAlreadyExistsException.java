package com.kutaysendil.siteManagement.exception.role;

import com.kutaysendil.siteManagement.exception.BaseException;

public class RoleAlreadyExistsException extends BaseException {
    public RoleAlreadyExistsException(String message) {
        super("ROLE_002", message);
    }
}