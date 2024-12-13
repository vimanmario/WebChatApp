package com.example.webchatapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender; // Numele utilizatorului
    private String content; // Conținutul mesajului
    private LocalDateTime timestamp; // Timpul în care a fost trimis mesajul

    @ManyToOne
    @JoinColumn(name="conversation_id")
    @JsonBackReference
    private Conversation conversation;

    // Constructori, getter și setter
    public Message() {}

    public Message(String sender, String content, LocalDateTime timestamp, Conversation conversation) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.conversation = conversation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
