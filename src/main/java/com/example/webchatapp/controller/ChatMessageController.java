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
        Long conversationId = 1L; // Conversația generală implicită

        try {
            // Transformăm JSON-ul într-un obiect
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode messageNode = (ObjectNode) objectMapper.readTree(messageJson);

            sender = messageNode.get("sender").asText();
            content = messageNode.get("content").asText();
            if (messageNode.has("conversationId")) {
                conversationId = messageNode.get("conversationId").asLong(); // Extragem ID-ul conversației
            }

            // Salvăm mesajul în baza de date folosind `MessageService`
            Message savedMessage = messageService.saveMessage(conversationId, sender, content);

            // Pregătim răspunsul pentru a-l trimite pe `/topic/public`
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", savedMessage.getId());
            response.put("sender", savedMessage.getSender());
            response.put("content", savedMessage.getContent());
            response.put("conversationId", conversationId);
            response.put("timestamp", savedMessage.getTimestamp().toString());

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
        if (conversationId == null) {
            conversationId = 1L; // Implicit conversația generală
        }
        return messageService.getMessagesByConversation(conversationId);
    }

}

