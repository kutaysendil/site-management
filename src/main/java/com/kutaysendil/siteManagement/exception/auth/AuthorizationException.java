package com.kutaysendil.siteManagement.exception.auth;


import com.kutaysendil.siteManagement.exception.BaseException;

public class AuthorizationException extends BaseException {
    public AuthorizationException(String message) {
        super(message, "AUTH_003");
    }
}
