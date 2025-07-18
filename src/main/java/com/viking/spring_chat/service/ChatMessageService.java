package com.viking.spring_chat.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.viking.spring_chat.dto.ChatMessageRequest;
import com.viking.spring_chat.dto.ChatMessageResponse;
import com.viking.spring_chat.entity.ChatMessage;
import com.viking.spring_chat.entity.ChatRoom;
import com.viking.spring_chat.entity.User;
import com.viking.spring_chat.exception.ChatRoomNotFoundException;
import com.viking.spring_chat.mapper.ChatMessageMapper;
import com.viking.spring_chat.repository.ChatMessageRepository;
import com.viking.spring_chat.repository.ChatRoomRepository;
import com.viking.spring_chat.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {
    
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageResponse saveMessage(ChatMessageRequest request, String senderUsername) {
        User sender = userRepository.findByEmail(senderUsername)
            .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + senderUsername));
        
        ChatRoom room = chatRoomRepository.findById(request.getRoomId())
            .orElseThrow(() -> new ChatRoomNotFoundException("Комната не найдена: " + request.getRoomId()));

        ChatMessage message = chatMessageMapper.toEntity(request, sender, room);
        ChatMessage saved = chatMessageRepository.save(message);

        return chatMessageMapper.toResponse(saved);
    }

    public List<ChatMessageResponse> getMessagesByRoomId(Long roomId) {
        List<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
        return messages.stream()
                .map(chatMessageMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ChatMessageResponse> getMessagesBefore(Long roomId, LocalDateTime before, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<ChatMessage> messages = chatMessageRepository.findByRoomIdAndCreatedAtBeforeOrderByCreatedAtDesc(roomId, before, pageable);
        Collections.reverse(messages);
        return messages.stream()
                .map(chatMessageMapper::toResponse)
                .collect(Collectors.toList());
    }
}
