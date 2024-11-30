package com.kutaysendil.siteManagement.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {
    private final String message;
    private final String code;

    protected BaseException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
