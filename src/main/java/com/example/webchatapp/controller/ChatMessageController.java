package com.example.webchatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

    // Metoda care gestionează mesajele primite pe "/chat.sendMessage" și le trimite către "/topic/public".
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(String message) {
        return message; // Mesajul este trimis către toți abonații la "/topic/public".
    }
}

