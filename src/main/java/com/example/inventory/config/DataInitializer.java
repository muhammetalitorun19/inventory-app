package com.example.inventory.config;

import com.example.inventory.model.Role;
import com.example.inventory.model.User;
import com.example.inventory.repository.RoleRepository;
import com.example.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            // ADMIN rolü varsa al, yoksa oluştur
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        System.out.println("🛠 ADMIN oluşturuluyor...");
                        return roleRepository.save(new Role(null, "ROLE_ADMIN"));
                    });

            // ADMIN kullanıcı zaten varsa bilgi ver
            if (userRepository.existsByUsername("admin")) {
                System.out.println("ℹ️ Admin zaten mevcut.");
                return;
            }

            // Admin yoksa oluştur
            User adminUser = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(adminUser);
            System.out.println("✅ Admin kullanıcısı oluşturuldu.");
        } catch (Exception e) {
            System.err.println("❌ DataInitializer içinde hata: " + e.getMessage());
        }
    }
}