package com.example.webchatapp.controller;

import com.example.webchatapp.model.Message;
import com.example.webchatapp.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageRestController {
    private final MessageService messageService;

    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/api/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/api/messages/{conversationId}")
    public List<Message> getMessagesByConversation(@PathVariable Long conversationId) {
        return messageService.getMessagesByConversation(conversationId);
    }

}
