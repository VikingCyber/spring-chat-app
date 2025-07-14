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
public class ChatMessageResponse {
    private Long messageId;      // id сообщения
    private Long roomId;         // id комнаты
    private String sender;       // имя или логин отправителя
    private String content;      // текст сообщения
    private LocalDateTime createdAt;  // время создания
}

