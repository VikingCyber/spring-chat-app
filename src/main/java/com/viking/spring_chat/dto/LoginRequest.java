package com.viking.spring_chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "Запрос для аутентификации пользователя")
public record LoginRequest(
    
    @Schema(description = "Email пользователя", example = "tuesr123@example.com")
    String email,
    
    @Schema(description = "Пароль пользователя", example = "strongpassword123")
    String password
) {}
