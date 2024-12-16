package com.example.webchatapp.repository;

import com.example.webchatapp.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    // Căutăm conversațiile în care userId este participant
    @Query("SELECT c FROM Conversation c JOIN c.participants p WHERE p.id = :userId")
    List<Conversation> findByUserId(@Param("userId") Long userId);

    // Verifică dacă există conversație între user1 și user2
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Conversation c JOIN c.participants p1 JOIN c.participants p2 WHERE p1.id = :user1 AND p2.id = :user2")
    boolean existsByParticipants(@Param("user1") Long user1, @Param("user2") Long user2);
}
