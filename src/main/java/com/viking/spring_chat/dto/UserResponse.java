package com.viking.spring_chat.dto;

public record UserResponse(
    Long id,
    String username,
    String email
) {}
