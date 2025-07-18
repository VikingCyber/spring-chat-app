package com.viking.spring_chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JwtResponse", description = "Ответ с JWT токеном")
public record JwtResponse(

    @Schema(description = "JWT токен", example = "eyJhbGciOiJIUzUxMiJ9...")
    String token,

    @Schema(description = "Имя пользователя", example = "tuser")
    String username
) {}
