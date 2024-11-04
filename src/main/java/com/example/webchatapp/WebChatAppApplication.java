package com.example.webchatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.webchatapp.model")
@EnableJpaRepositories("com.example.webchatapp.repository")
public class WebChatAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebChatAppApplication.class, args);
    }

}
