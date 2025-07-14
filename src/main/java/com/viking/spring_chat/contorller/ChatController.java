package com.viking.spring_chat.contorller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viking.spring_chat.dto.ApiResponse;
import com.viking.spring_chat.dto.ChatMessageResponse;
import com.viking.spring_chat.dto.ChatRoomRequest;
import com.viking.spring_chat.dto.ChatRoomResponse;
import com.viking.spring_chat.service.ChatMessageService;
import com.viking.spring_chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatRoomResponse>>> getAllRooms() {
        List<ChatRoomResponse> rooms = chatRoomService.getAllRooms();
        ApiResponse<List<ChatRoomResponse>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Комнаты успешно получены",
            rooms,
            LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ChatRoomResponse>> createRoom(@RequestBody ChatRoomRequest request) {
        ChatRoomResponse createdRoom = chatRoomService.createRoom(request);
        ApiResponse<ChatRoomResponse> response = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Комната успешно создана",
            createdRoom,
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long id) {
        chatRoomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChatRoomResponse>> getRoomById(@PathVariable Long id) {
        ChatRoomResponse response = chatRoomService.getRoomById(id);
        ApiResponse<ChatRoomResponse> apiResponse = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Комната успешно найдена",
            response,
            LocalDateTime.now()
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getMessagesByRoomId(@PathVariable Long roomId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
            @RequestParam(defaultValue = "50") int limit) {

        LocalDateTime beforeTime = before != null ? before : LocalDateTime.now();
        List<ChatMessageResponse> messages = chatMessageService.getMessagesBefore(roomId, beforeTime, limit);
        ApiResponse<List<ChatMessageResponse>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Сообщения успешно получена",
            messages,
            LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
}
