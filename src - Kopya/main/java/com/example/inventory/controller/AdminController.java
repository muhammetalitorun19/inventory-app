package com.example.inventory.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/panel")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanel() {
        return "🔒 Bu alana sadece ADMIN kullanıcıları erişebilir!";
    }
}
