package com.viking.spring_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viking.spring_chat.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
