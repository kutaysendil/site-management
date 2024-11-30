package com.kutaysendil.siteManagement.exception.user;

import com.kutaysendil.siteManagement.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message, "USER_001");
    }
}