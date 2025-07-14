package com.viking.spring_chat.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponse {
    private Long id;
    private String name;
    private String description;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private ChatRoomTypeResponse type;
}
