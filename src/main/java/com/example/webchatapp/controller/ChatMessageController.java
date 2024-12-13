package com.example.webchatapp.controller;

import com.example.webchatapp.model.Message;
import com.example.webchatapp.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class ChatMessageController {
    private final MessageService messageService;

    public ChatMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Metoda care gestionează mesajele primite pe "/chat.sendMessage" și le trimite către "/topic/public".
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(String messageJson) {
        String sender = "Anonymous";
        String content = "";
        Long conversationId = null;

        try {
            // Transformă JSON-ul într-un obiect
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode messageNode = (ObjectNode) objectMapper.readTree(messageJson);

            sender = messageNode.get("sender").asText();
            content = messageNode.get("content").asText();
            conversationId = messageNode.get("conversationId").asLong(); // Extrage ID-ul conversației

            // Salvăm mesajul în baza de date
            messageService.saveMessage(conversationId, sender, content);

            // Pregătim răspunsul
            ObjectNode response = objectMapper.createObjectNode();
            response.put("sender", sender);
            response.put("content", content);
            response.put("conversationId", conversationId);

            return response.toString();
        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
            return "{\"sender\": \"Error\", \"content\": \"Message failed\"}";
        }
    }

    // Metoda care trimite toate mesajele salvate la încărcarea aplicației
    @MessageMapping("/chat.loadMessagesByConversation")
    @SendTo("/topic/public")
    public List<Message> loadMessagesByConversation(Long conversationId) {
        return messageService.getMessagesByConversation(conversationId);
    }
}

