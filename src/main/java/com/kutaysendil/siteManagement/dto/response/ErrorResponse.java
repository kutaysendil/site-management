package com.kutaysendil.siteManagement.dto.response;


import com.kutaysendil.siteManagement.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private String timestamp;
    private String path;
    private List<ValidationError> hatalar;

    public static ErrorResponse of(String code, String message, String path) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .timestamp(DateUtils.nowTurkeyAsString())
                .path(path)
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}