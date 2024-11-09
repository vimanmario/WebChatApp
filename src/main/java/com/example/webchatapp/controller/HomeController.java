package com.example.webchatapp.controller;

import com.example.webchatapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @GetMapping("/")
    public RedirectView home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return new RedirectView("/chat");
        }
        return new RedirectView("/login");
    }
}
