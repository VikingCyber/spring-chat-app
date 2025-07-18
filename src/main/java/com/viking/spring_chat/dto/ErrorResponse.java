package com.viking.spring_chat.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Структура ошибки")
public record ErrorResponse(

    @Schema(description = "HTTP статус", example = "401")
    int status,

    @Schema(description = "Краткое описание ошибки", example = "Неверный email или пароль")
    String error,

    @Schema(description = "Подробное сообщение об ошибке", example = "Указаны неверные учетные данные")
    String message,

    @Schema(description = "Путь запроса, где произошла ошибка", example = "/api/auth/login")
    String path,

    @Schema(description = "Время возникновения ошибки", example = "2025-07-17T22:15:30.123456")
    LocalDateTime timestamp
) {}
