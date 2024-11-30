package com.kutaysendil.siteManagement.exception.auth;

import com.kutaysendil.siteManagement.exception.BaseException;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(String message) {
        super(message, "AUTH_002");
    }
}