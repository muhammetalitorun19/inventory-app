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
            System.out.println("▶️ DataInitializer başladı");

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        System.out.println("➕ ROLE_ADMIN oluşturuluyor");
                        return roleRepository.save(new Role(null, "ROLE_ADMIN"));
                    });

            System.out.println("✅ Rol tamam");

            if (!userRepository.existsByUsername("admin")) {
                System.out.println("➕ Admin oluşturuluyor");

                User admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("admin123"))
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(admin);
                System.out.println("✅ Admin kaydedildi");
            } else {
                System.out.println("ℹ️ Admin zaten var");
            }

            System.out.println("🏁 DataInitializer tamamlandı");

        } catch (Exception e) {
            System.err.println("❌ DataInitializer çalışırken hata oluştu:");
            e.printStackTrace();
        }
    }
}

