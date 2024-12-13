package com.example.webchatapp.repository;

import com.example.webchatapp.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    //
}
