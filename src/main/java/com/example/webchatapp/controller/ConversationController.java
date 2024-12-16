package com.example.webchatapp.controller;

import com.example.webchatapp.model.Conversation;
import com.example.webchatapp.model.User;
import com.example.webchatapp.service.ConversationService;
import com.example.webchatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Conversation createConversation(@RequestBody Map<String, Object> requestData) {
        String name = (String) requestData.get("name");
        boolean isGeneral = (boolean) requestData.get("isGeneral");

        // Convertim participantIds în List<Long>
        List<Long> participantIds = ((List<Integer>) requestData.get("participantIds"))
                .stream()
                .map(Integer::longValue) // Convertim Integer la Long
                .collect(Collectors.toList());

        List<User> participants = new ArrayList<>();
        if (!isGeneral) {
            participants = participantIds.stream()
                    .map(userService::getUserById)
                    .collect(Collectors.toList());
        }

        return conversationService.createConversation(name, isGeneral, participants);
    }


    @GetMapping
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversations();
    }
}