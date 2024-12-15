package com.example.webchatapp.service;

import com.example.webchatapp.model.Conversation;
import com.example.webchatapp.model.User;
import com.example.webchatapp.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation createConversation(String name, boolean isGeneral, List<User> participants) {
        if (!isGeneral && participants == null) {
            throw new IllegalArgumentException("O conversație privată trebuie să aibă participanți!");
        }

        Conversation conversation = new Conversation(name, isGeneral, participants);
        return conversationRepository.save(conversation);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conversația nu există!"));
    }
}