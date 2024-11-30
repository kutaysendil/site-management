package com.kutaysendil.siteManagement.exception;

import com.kutaysendil.siteManagement.dto.response.ErrorResponse;
import com.kutaysendil.siteManagement.exception.auth.AuthenticationException;
import com.kutaysendil.siteManagement.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(
            String code,
            String message,
            String path,
            HttpStatus status,
            List<ErrorResponse.ValidationError> errors) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .hataKodu(code)
                .mesaj(message)
                .zaman(DateUtils.nowTurkeyAsString())
                .url(path)
                .hatalar(errors)
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, WebRequest request) {
        log.error("Base exception occurred: ", ex);
        return createErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                getPath(request),
                HttpStatus.BAD_REQUEST,
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.ValidationError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return createErrorResponse(
                "VALIDATION_001",
                "Kullanıcı doğrulanamadı",
                getPath(request),
                HttpStatus.BAD_REQUEST,
                validationErrors
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex, WebRequest request) {
        log.error("Authentication exception occurred: ", ex);
        return createErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                getPath(request),
                HttpStatus.UNAUTHORIZED,
                null
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        log.error("Access denied exception occurred: ", ex);
        return createErrorResponse(
                "AUTH_004",
                "Erişim engellendi",
                getPath(request),
                HttpStatus.FORBIDDEN,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected exception occurred: ", ex);
        return createErrorResponse(
                "INTERNAL_001",
                "Beklenmedik bir hata oluştu",
                getPath(request),
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );
    }
}