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

    public List<Conversation> getUserConversations(Long userId) {
        // Filtrăm conversațiile la care utilizatorul este participant
        return conversationRepository.findByUserId(userId);
    }

    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conversația nu există!"));
    }


    public boolean existsBetweenUsers(Long user1, Long user2) {
        // Verificăm dacă există o conversație între user1 și user2
        return conversationRepository.existsByParticipants(user1, user2);
    }

}