package com.example.webchatapp.service;

import com.example.webchatapp.model.Conversation;
import com.example.webchatapp.model.Message;
import com.example.webchatapp.model.MessageAttachment;
import com.example.webchatapp.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);


    private final MessageRepository messageRepository;
    private final ConversationService conversationService;

    @Autowired
    private FileStorageService fileStorageService;

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
        logger.info("Fetching messages for conversationId: {}", conversationId);
        try {
            List<Message> messages = messageRepository.findMessagesByConversationId(conversationId);
            logger.info("Found {} messages for conversationId: {}", messages.size(), conversationId);
            return messages;
        } catch (Exception e) {
            logger.error("Error fetching messages for conversationId: {}", conversationId, e);
            throw e;
        }
    }

    public Message saveMessageWithAttachment(Long conversationId, String sender,
                                             String content, MultipartFile file) throws IOException {
        Conversation conversation = conversationService.getConversationById(conversationId);
        Message message = new Message(sender, content, LocalDateTime.now(), conversation);
        message.setHasAttachment(true);

        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            MessageAttachment attachment = new MessageAttachment();
            attachment.setFileName(fileName);
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());
            attachment.setFileUrl("/api/files/" + fileName);
            attachment.setMessage(message);
            message.getAttachments().add(attachment);
        }

        return messageRepository.save(message);
    }

}
