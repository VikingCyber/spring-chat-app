package com.viking.spring_chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viking.spring_chat.entity.ChatRoomType;

public interface ChatRoomTypeRepository extends JpaRepository<ChatRoomType, Long> {
    Optional<ChatRoomType> findByName(String name);
}
