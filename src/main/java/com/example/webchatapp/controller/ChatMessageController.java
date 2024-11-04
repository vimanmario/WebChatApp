package com.example.webchatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(String message) {
        return message;
    }
}

