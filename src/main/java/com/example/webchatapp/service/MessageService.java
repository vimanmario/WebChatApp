package com.example.webchatapp.service;

import com.example.webchatapp.model.Conversation;
import com.example.webchatapp.model.Message;
import com.example.webchatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationService conversationService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationService conversationService) {
        this.messageRepository = messageRepository;
        this.conversationService = conversationService;
    }

    public Message saveMessage(Long conversationId, String sender, String content) {
        Conversation conversation = conversationService.getConversationById(conversationId);
        Message message = new Message(sender, content, LocalDateTime.now(), conversation);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAllMessagesSorted();
    }

    public List<Message> getMessagesByConversation(Long conversationId) {
        return messageRepository.findMessagesByConversationId(conversationId);
    }

}
