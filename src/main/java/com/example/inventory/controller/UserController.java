package com.example.inventory.controller;

import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Tüm kullanıcıları listele (sadece ADMIN erişebilir)
    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

