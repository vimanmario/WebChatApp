package com.example.webchatapp.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Processing cookie: " + cookie.getName() + ", value: " + cookie.getValue());
                if ("remember-me".equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setHttpOnly(false); // Testare
                    response.addCookie(cookie);
                    System.out.println("Cleared remember-me cookie.");
                }
            }
        } else {
            System.out.println("No cookies found to process.");
        }
    }
}
