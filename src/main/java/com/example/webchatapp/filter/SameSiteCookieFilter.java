package com.example.webchatapp.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SameSiteCookieFilter implements Filter {
    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Adaugă SameSite pentru toate cookie-urile
        chain.doFilter(request, new HttpServletResponseWrapper(httpResponse) {
            @Override
            public void addCookie(Cookie cookie) {
                super.addCookie(cookie);
                String header = httpResponse.getHeader("Set-Cookie");
                if (header != null && !header.contains("SameSite")) {
                    httpResponse.setHeader("Set-Cookie", header + "; SameSite=Strict");
                }
            }
        });
    }
}
