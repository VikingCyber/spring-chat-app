package com.viking.spring_chat.contorller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viking.spring_chat.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    
        @GetMapping("/secure-test")
    public ResponseEntity<ApiResponse<String>> secureTest() {
        ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Доступ разрешен",
            "Ты авторизован!",
            LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
}
