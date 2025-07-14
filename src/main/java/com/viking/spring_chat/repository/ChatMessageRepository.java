package com.viking.spring_chat.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.viking.spring_chat.Entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(Long roomId);
    List<ChatMessage> findByRoomIdAndCreatedAtBeforeOrderByCreatedAtDesc(Long roomId, LocalDateTime before, Pageable pageable);
}
