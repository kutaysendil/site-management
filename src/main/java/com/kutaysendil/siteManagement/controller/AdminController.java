package com.kutaysendil.siteManagement.controller;

import com.kutaysendil.siteManagement.annotation.claims.AdminRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AdminRequired
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/users")

    public String getAllUsers() {
        return "getallusers";
    }

    @GetMapping("/bills")
    public String getAllBills() {
        return "getAllBills";
    }
}
