package com.kutaysendil.siteManagement.exception.claim;


import com.kutaysendil.siteManagement.exception.BaseException;

public class ClaimNotFoundException extends BaseException {
    public ClaimNotFoundException(String message) {
        super(message, "CLAIM_001");
    }
}
