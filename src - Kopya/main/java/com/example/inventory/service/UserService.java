package com.example.inventory.service;

import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Kullanıcı yükleniyor3: " + username);

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ Kullanıcı bulunamadi: " + username);
                    return new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
                });

        var authorities = user.getRoles().stream()
                .map(role -> {
                    System.out.println("🎯 Rol bulundu: " + role.getName());
                    return new SimpleGrantedAuthority("ROLE_" + role.getName());
                })
                .collect(Collectors.toList());

        System.out.println("✅ Kullanıcı başarıyla yüklendi: " + user.getUsername());
        System.out.println("📜 Yetkiler: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // 👈 DİKKAT
                        .collect(Collectors.toList())
        );

    }
}
