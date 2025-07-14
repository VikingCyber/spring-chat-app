package com.viking.spring_chat.mapper;

import org.springframework.stereotype.Component;

import com.viking.spring_chat.Entity.ChatRoom;
import com.viking.spring_chat.Entity.ChatRoomType;
import com.viking.spring_chat.dto.ChatRoomRequest;
import com.viking.spring_chat.dto.ChatRoomResponse;
import com.viking.spring_chat.dto.ChatRoomTypeResponse;

@Component
public class ChatRoomMapper {
    
    public ChatRoom toEntity(ChatRoomRequest request, ChatRoomType type) {
        return ChatRoom.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(type)
                .build();
    }

    public ChatRoomResponse toResponse(ChatRoom room) {
        return ChatRoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .avatarUrl(room.getAvatarUrl())
                .createdAt(room.getCreatedAt())
                .type(
                    room.getType() != null
                    ? ChatRoomTypeResponse.builder()
                        .id(room.getType().getId())
                        .name(room.getType().getName())
                        .build()
                    : null
                )
                .build();
    }
}
