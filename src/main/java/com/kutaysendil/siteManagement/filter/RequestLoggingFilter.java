package com.kutaysendil.siteManagement.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);

        try {
            // Request bilgilerini logla
            log.info("[{}] REQUEST {} {} - Headers: {} - Body: {}",
                    requestId,
                    request.getMethod(),
                    request.getRequestURI(),
                    getImportantHeaders(request),  // Sadece önemli header'ları al
                    getRequestBody(requestWrapper));

            filterChain.doFilter(requestWrapper, responseWrapper);

            // Response Log
            long duration = System.currentTimeMillis() - startTime;
            log.info("[{}] RESPONSE Status: {} - Duration: {}ms - Body: {}",
                    requestId,
                    responseWrapper.getStatus(),
                    duration,
                    getResponseBody(responseWrapper));

        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }

    private Map<String, String> getImportantHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        // Sadece önemli header'ları al
        List<String> importantHeaders = Arrays.asList(
                "Authorization", "Content-Type", "Accept"
        );

        for (String header : importantHeaders) {
            String value = request.getHeader(header);
            if (value != null) {
                headers.put(header, value);
            }
        }
        return headers;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        try {
            String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
            return StringUtils.hasText(body) ? body : "empty";
        } catch (Exception e) {
            return "could not read body";
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        try {
            String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
            return StringUtils.hasText(body) ? body : "empty";
        } catch (Exception e) {
            return "could not read body";
        }
    }
}