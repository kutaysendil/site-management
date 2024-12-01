package com.kutaysendil.siteManagement.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message, "CORE_001");
    }
}