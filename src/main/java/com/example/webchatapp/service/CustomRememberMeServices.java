package com.example.webchatapp.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class CustomRememberMeServices extends PersistentTokenBasedRememberMeServices {
    public CustomRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        super.setCookie(tokens, maxAge, request, response);
        Cookie cookie = new Cookie("remember-me", String.join(":", tokens));
        cookie.setHttpOnly(false); // Sau true pentru securitate
        cookie.setSecure(true); // Pentru HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "Strict"); // Adaugă manual SameSite
        response.addCookie(cookie);
    }
}
