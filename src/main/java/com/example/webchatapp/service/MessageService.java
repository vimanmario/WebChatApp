package com.example.webchatapp.service;

import com.example.webchatapp.model.Message;
import com.example.webchatapp.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(String sender, String content) {
        Message message = new Message(sender, content, LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAllMessagesSorted();
    }
}
