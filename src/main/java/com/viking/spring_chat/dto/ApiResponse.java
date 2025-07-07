package com.viking.spring_chat.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
    int status,
    String message,
    T data,
    LocalDateTime timestamp
) {}
