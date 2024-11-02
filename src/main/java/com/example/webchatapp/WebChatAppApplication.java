package com.example.webchatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.webchatapp.model")
public class WebChatAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebChatAppApplication.class, args);
    }

}
