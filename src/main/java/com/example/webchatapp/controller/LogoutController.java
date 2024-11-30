package com.example.webchatapp.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("Logout endpoint hit"); // Debug 1

        // Șterge JSESSIONID
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        System.out.println("JSESSIONID cookie deleted"); // Debug 2

        // Șterge remember-me
        Cookie rememberMeCookie = new Cookie("remember-me", null);
        rememberMeCookie.setHttpOnly(true);
        rememberMeCookie.setPath("/");
        rememberMeCookie.setMaxAge(0);
        response.addCookie(rememberMeCookie);
        System.out.println("Remember-me cookie deleted"); // Debug 3

        //request.getSession().invalidate();
        // Invalidate session
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
            System.out.println("Session invalidated"); // Debug 4
        } else {
            System.out.println("No session found to invalidate"); // Debug 5
        }

        return ResponseEntity.ok().build();
    }
}
