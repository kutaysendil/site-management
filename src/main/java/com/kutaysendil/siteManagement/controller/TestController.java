package com.kutaysendil.siteManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        try {
            // Veritabanı bağlantısını kontrol et
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return ResponseEntity.ok("yes");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("no");
        }
    }

    @GetMapping("/db")
    public ResponseEntity<String> dbCheck() {
        try {
            // Daha detaylı DB kontrolü
            String result = jdbcTemplate.queryForObject(
                    "SELECT current_database()", String.class);
            return ResponseEntity.ok("Database: " + result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Database connection failed: " + e.getMessage());
        }
    }
}