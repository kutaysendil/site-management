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
    private String hataKodu;
    private String mesaj;
    private String zaman;
    private String url;
    private List<ValidationError> hatalar;

    public static ErrorResponse of(String code, String message, String path) {
        return ErrorResponse.builder()
                .hataKodu(code)
                .mesaj(message)
                .zaman(DateUtils.nowTurkeyAsString())
                .url(path)
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