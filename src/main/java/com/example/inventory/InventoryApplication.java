package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = "com.example.inventory.model")
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    // ✅ Ana sınıfa gömülü test endpoint
    @RestController
    class DebugController {
        @GetMapping("/")
        public String healthCheck() {
            return "Direct from InventoryApplication - Uygulama çalışıyor!";
        }
    }
}
