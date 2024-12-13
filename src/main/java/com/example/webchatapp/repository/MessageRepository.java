package com.example.webchatapp.repository;

import com.example.webchatapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Query personalizat pentru sortare cronologică
    @Query("SELECT m FROM Message m ORDER BY m.timestamp ASC")
    List<Message> findAllMessagesSorted();

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId ORDER BY m.timestamp ASC")
    List<Message> findMessagesByConversationId(@Param("conversationId") Long conversationId);

}
