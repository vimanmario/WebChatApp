package com.example.webchatapp.controller;

import com.example.webchatapp.model.Conversation;
import com.example.webchatapp.model.User;
import com.example.webchatapp.service.ConversationService;
import com.example.webchatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    @Autowired
    private UserService userService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public Conversation createConversation(@RequestParam String name,
                                           @RequestParam boolean isGeneral,
                                           @RequestParam(required = false) List<Long> participantIds) {
        // Dacă este o conversație privată, obținem utilizatorii pe baza IDs
        List<User> participants = new ArrayList<>();

        if (!isGeneral && participantIds != null) {
            // Căutăm utilizatorii din baza de date folosind serviciul UserService
            for (Long participantId : participantIds) {
                User user = userService.getUserById(participantId); // Îți propun să adaugi metoda getUserById în UserService
                participants.add(user);
            }
        }

        // Creăm și salvăm conversația
        return conversationService.createConversation(name, isGeneral, participants);
    }

    @GetMapping
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversations();
    }
}