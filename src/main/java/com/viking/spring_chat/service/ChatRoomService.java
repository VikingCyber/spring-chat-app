package com.viking.spring_chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viking.spring_chat.dto.ChatRoomRequest;
import com.viking.spring_chat.dto.ChatRoomResponse;
import com.viking.spring_chat.entity.ChatRoom;
import com.viking.spring_chat.entity.ChatRoomType;
import com.viking.spring_chat.exception.ChatRoomNotFoundException;
import com.viking.spring_chat.exception.ChatRoomTypeNotFoundException;
import com.viking.spring_chat.mapper.ChatRoomMapper;
import com.viking.spring_chat.repository.ChatRoomRepository;
import com.viking.spring_chat.repository.ChatRoomTypeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomTypeRepository chatRoomTypeRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Transactional
    public ChatRoomResponse createRoom(ChatRoomRequest request) {
        ChatRoomType type = chatRoomTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new ChatRoomTypeNotFoundException("Тип комнаты не найден с id: " + request.getTypeId()));

        ChatRoom room = chatRoomMapper.toEntity(request, type);
        ChatRoom savedRoom = chatRoomRepository.save(room);
        return chatRoomMapper.toResponse(savedRoom);
    }

    public List<ChatRoomResponse> getAllRooms() {
        return chatRoomRepository.findAll().stream()
                .map(chatRoomMapper::toResponse)
                .toList();
    }

    public void deleteRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("Комната не найдена с id: " + roomId));
            chatRoomRepository.delete(room);
    }

    public ChatRoomResponse getRoomById(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException("Комната с id: " + id + " не найдена"));
        return chatRoomMapper.toResponse(chatRoom);
    }

}
