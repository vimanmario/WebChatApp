package com.example.webchatapp.repository;

import com.example.webchatapp.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Conversation c " +
            "JOIN c.participants p1 " +
            "JOIN c.participants p2 " +
            "WHERE (p1.id = :user1 AND p2.id = :user2) " +
            "OR (p1.id = :user2 AND p2.id = :user1)")
    boolean existsByParticipants(@Param("user1") Long user1, @Param("user2") Long user2);
}
