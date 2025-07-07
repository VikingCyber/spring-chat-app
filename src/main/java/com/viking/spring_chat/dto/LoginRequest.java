package com.viking.spring_chat.dto;

public record LoginRequest(
    String email, 
    String password
) {}
