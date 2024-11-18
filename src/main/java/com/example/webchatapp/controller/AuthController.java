package com.example.webchatapp.controller;

import com.example.webchatapp.model.User;
import com.example.webchatapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

//    @PostMapping("/logout")
//    public String manualLogout(HttpSession session) {
//        session.invalidate(); // Invalidează sesiunea curentă
//        SecurityContextHolder.clearContext(); // Șterge contextul de securitate
//        return "redirect:/login";
//    }


}
