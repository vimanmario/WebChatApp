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
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("Invalidating session with ID: " + session.getId());
            session.invalidate();
        }

        // Șterge cookie-urile
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("Clearing cookie: " + cookie.getName());
                cookie.setValue(null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie); // Adaugă cookie-ul cu valoare goală
            }
        }

        logger.info("Cookies after logout: " + Arrays.toString(request.getCookies()));
    }
}
