package com.kutaysendil.siteManagement.exception.claim;

import com.kutaysendil.siteManagement.exception.BaseException;

public class ClaimAlreadyExistsException extends BaseException {
    public ClaimAlreadyExistsException(String message) {
        super(message, "CLAIM_002");
    }
}