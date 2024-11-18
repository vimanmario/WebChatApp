package com.example.webchatapp.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

// Folosit pentru  a monitoriza detalii despre sessiuni si cookie-uri , in special pt debugging in caz de nevoie
// Chestiile de coocki-uri nu doar pentru NoCacheFilter
public class SessionDebugFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionDebugFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("SessionDebugFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            logger.info("No session found for request to {}", httpRequest.getRequestURI());
        } else {
            logger.info("Session found with ID: {} for request to {}", session.getId(), httpRequest.getRequestURI());
            logger.info("Cookies: {}", Arrays.toString(httpRequest.getCookies()));  // Loghează cookies-urile
        }

        // Log cookie details
        if (httpRequest.getCookies() != null) {
            Arrays.stream(httpRequest.getCookies()).forEach(cookie ->
                    logger.info("Cookie name: {}, value: {}, path: {}, maxAge: {}",
                            cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getMaxAge())
            );
        }

        // Log security context
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("Authentication principal: {}", authentication.getPrincipal());
            logger.info("Authorities: {}", authentication.getAuthorities());
        } else {
            logger.info("No authentication context available for request to {}", httpRequest.getRequestURI());
        }

        chain.doFilter(request, response);
    }

    // Nu e necesar momentan
    @Override
    public void destroy() {
        // Curățare filtru dacă este necesar
        logger.info("SessionDebugFilter destroyed");
    }
}
