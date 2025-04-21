package com.example.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String index() {
        return "✅ API çalışıyor! Hoş geldin.";
    }

    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK ✅";
    }
}

