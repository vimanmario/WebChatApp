package com.example.webchatapp.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.context.annotation.Bean;

@Configuration
public class CookieConfig {
    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("remember-me");
        serializer.setUseHttpOnlyCookie(false); // Permite accesul prin JavaScript
        serializer.setUseSecureCookie(true); // Dacă folosești HTTPS



        return serializer;
    }
}
