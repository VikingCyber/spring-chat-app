package com.viking.spring_chat.contorller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.viking.spring_chat.dto.ChatMessageRequest;
import com.viking.spring_chat.dto.ChatMessageResponse;
import com.viking.spring_chat.service.ChatMessageService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage") // клиент шлёт сюда
    public void sendMessage(@Payload ChatMessageRequest request,
                            Principal principal) {
        log.info("Получено сообщение от {} в комнату {}: {}", principal.getName(), request.getRoomId(), request.getContent());
        ChatMessageResponse savedMessage = chatMessageService.saveMessage(request, principal.getName());
        messagingTemplate.convertAndSend("/topic/chat/" + request.getRoomId(), savedMessage);
    }
}

