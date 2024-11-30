package com.kutaysendil.siteManagement.exception.auth;

import com.kutaysendil.siteManagement.exception.BaseException;

public class EmailAlreadyExistsException extends BaseException {
    public EmailAlreadyExistsException(String message) {
        super(message, "AUTH_004");
    }
}