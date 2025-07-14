package com.viking.spring_chat.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.viking.spring_chat.Entity.ChatMessage;
import com.viking.spring_chat.Entity.ChatRoom;
import com.viking.spring_chat.Entity.User;
import com.viking.spring_chat.dto.ChatMessageRequest;
import com.viking.spring_chat.dto.ChatMessageResponse;

@Component
public class ChatMessageMapper {

    public ChatMessage toEntity(ChatMessageRequest request, User sender, ChatRoom room) {
        return ChatMessage.builder()
                .room(room)
                .sender(sender)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ChatMessageResponse toResponse(ChatMessage message) {
        return ChatMessageResponse.builder()
                .messageId(message.getId())
                .roomId(message.getRoom().getId())
                .sender(message.getSender().getUsername())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}

