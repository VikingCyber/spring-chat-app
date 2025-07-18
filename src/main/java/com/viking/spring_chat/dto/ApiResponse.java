package com.viking.spring_chat.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponse", description = "Обертка стандартного ответа API")
public record ApiResponse<T>(
    
    @Schema(description = "HTTP статус", example = "200")
    int status,
    
    @Schema(description = "Сообщение", example = "Аутентификация успешна")
    String message,

    @Schema(description = "Данные ответа")
    T data,

    @Schema(description = "Временная метка", example = "2025-07-14T20:28:10.910795767")
    LocalDateTime timestamp
) {}
