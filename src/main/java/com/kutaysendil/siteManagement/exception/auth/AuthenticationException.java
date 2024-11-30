package com.kutaysendil.siteManagement.exception.auth;

import com.kutaysendil.siteManagement.exception.BaseException;

public class AuthenticationException extends BaseException {
    public AuthenticationException(String message) {
        super(message, "AUTH_001");
    }
}