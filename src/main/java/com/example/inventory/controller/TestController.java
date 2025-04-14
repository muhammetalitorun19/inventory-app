package com.example.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String hello() {
        return "Uygulama başarıyla çalışıyor!";
    }
}

