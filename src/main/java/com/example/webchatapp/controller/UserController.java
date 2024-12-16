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
import java.util.stream.Collectors;

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

        // Folosim UserService pentru a obține utilizatorul curent
        User currentUser = userService.findByUsername(username);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Returnăm un obiect JSON cu id și username
        return ResponseEntity.ok(Map.of(
                "id", currentUser.getId(),
                "username", currentUser.getUsername()
        ));
    }

    @GetMapping("/users")
    public List<User> getUsers(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
            }

            String username = authentication.getName();  // Obține utilizatorul curent
            List<User> allUsers = userService.getAllUsers();  // Obține toți utilizatorii

            // Filtrează utilizatorul curent
            return allUsers.stream()
                    .filter(user -> !user.getUsername().equals(username))  // Exclude utilizatorul curent
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load users", e);
        }
    }

}
