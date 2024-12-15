package com.example.webchatapp.controller;

import com.example.webchatapp.model.User;
import com.example.webchatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String username = authentication.getName(); // Obține numele utilizatorului autentificat.
        return ResponseEntity.ok(Map.of("username", username));
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace(); // Sau loghează într-un fișier de log
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load users", e);
        }
    }

}
