package com.example.webchatapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping
    public String chat(Model model, Authentication authentication) {
        // Verifică dacă utilizatorul este autentificat
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // Redirecționează la pagina de login dacă nu e autentificat
        }
        return "chat"; // Returnează pagina de chat pentru utilizatori autentificați
    }
}


