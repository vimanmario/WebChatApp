package com.example.webchatapp.controller;

import com.example.webchatapp.model.Conversation;
import com.example.webchatapp.model.User;
import com.example.webchatapp.repository.ConversationRepository;
import com.example.webchatapp.service.ConversationService;
import com.example.webchatapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ConversationController {
    private static final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    private final ConversationService conversationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationController(ConversationService conversationService, ConversationRepository conversationRepository) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
    }

    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(@RequestBody Map<String, Object> requestData) {
        String name = (String) requestData.get("name");
        boolean isGeneral = (boolean) requestData.get("isGeneral");

        List<Long> participantIds = ((List<Integer>) requestData.get("participantIds"))
                .stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        List<User> participants = new ArrayList<>();
        if (!isGeneral) {
            participants = participantIds.stream()
                    .map(userService::getUserById)
                    .collect(Collectors.toList());
        }

        if (participants.contains(null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Returnează un răspuns de eroare clar
        }

        Conversation createdConversation = conversationService.createConversation(name, isGeneral, participants);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdConversation);
    }

    // Endpoint pentru a obține doar conversațiile relevante pentru un utilizator
    @GetMapping("/conversations/user/{userId}")
    public ResponseEntity<List<Conversation>> getUserConversations(@PathVariable Long userId) {
        logger.info("Fetching conversations for userId: {}", userId);
        try {
            List<Conversation> conversations = conversationRepository.findByUserId(userId);  // Filtrăm după ID-ul utilizatorului
            logger.debug("Conversations fetched: {}", conversations);

            if (conversations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            e.printStackTrace(); // Log pentru debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkIfConversationExists(@RequestParam Long user1, @RequestParam Long user2){
        boolean exists = conversationService.existsBetweenUsers(user1, user2);
        return ResponseEntity.ok(exists);
    }
}