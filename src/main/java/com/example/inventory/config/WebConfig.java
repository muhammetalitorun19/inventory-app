package com.example.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint’ler için geçerli
                .allowedOrigins("http://localhost:4200", "https://inventory-app-production-76ef.up.railway.app") // 🔥 frontend adresi
                .allowedMethods("*") // GET, POST, PUT, DELETE, etc.
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}


