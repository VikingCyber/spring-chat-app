package com.viking.spring_chat.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.viking.spring_chat.dto.RegistrationRequest;
import com.viking.spring_chat.entity.GlobalRole;
import com.viking.spring_chat.entity.User;

@Component
public class UserMapper {
    public User toEntity(RegistrationRequest request, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(GlobalRole.ROLE_USER)
                .enabled(true)
                .build();
    }
}
