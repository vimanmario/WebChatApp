package com.example.webchatapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Numele conversației (e.g., "General", "Private-User1-User2")

    private boolean isGeneral; // Flag pentru conversația generală

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "conversation")
    @JsonManagedReference // Previne recursivitatea
    private List<Message> messages;

    // Constructori, getter și setter
    public Conversation() {}

    public Conversation(String name, boolean isGeneral) {
        this.name = name;
        this.isGeneral = isGeneral;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGeneral() {
        return isGeneral;
    }

    public void setGeneral(boolean general) {
        isGeneral = general;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}