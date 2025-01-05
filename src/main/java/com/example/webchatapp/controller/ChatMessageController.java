package com.example.webchatapp.controller;

import com.example.webchatapp.model.Message;
import com.example.webchatapp.model.MessageAttachment;
import com.example.webchatapp.service.MessageService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        List<MessageAttachment> attachments = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode messageNode = (ObjectNode) objectMapper.readTree(messageJson);

            sender = messageNode.get("sender").asText();
            content = messageNode.get("content").asText();
            if (messageNode.has("conversationId")) {
                conversationId = messageNode.get("conversationId").asLong();
            }

            // Procesăm atașamentele din JSON
            if (messageNode.has("attachments")) {
                for (JsonNode attachmentNode : messageNode.withArray("attachments")) {
                    MessageAttachment attachment = new MessageAttachment();
                    attachment.setFileName(attachmentNode.get("storedName").asText());
                    attachment.setFileType(attachmentNode.get("type").asText());
                    attachment.setFileUrl("/api/files/" + attachmentNode.get("storedName").asText());
                    attachment.setFileSize(0L); // Setăm dimensiunea fișierului dacă este necesar
                    attachments.add(attachment);
                }
            }

            // Salvăm mesajul în baza de date, incluzând atașamentele
            Message savedMessage = messageService.saveMessageWithAttachments(conversationId, sender, content, attachments);

            // Pregătim răspunsul pentru client
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", savedMessage.getId());
            response.put("sender", savedMessage.getSender());
            response.put("content", savedMessage.getContent());
            response.put("conversationId", conversationId);
            response.put("timestamp", savedMessage.getTimestamp().toString());

            ArrayNode attachmentsArray = response.putArray("attachments");
            for (MessageAttachment attachment : savedMessage.getAttachments()) {
                ObjectNode attachmentNode = objectMapper.createObjectNode();
                attachmentNode.put("fileName", attachment.getFileName());
                attachmentNode.put("fileType", attachment.getFileType());
                attachmentNode.put("fileUrl", attachment.getFileUrl());
                attachmentsArray.add(attachmentNode);
            }

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

