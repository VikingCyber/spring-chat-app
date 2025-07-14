package com.viking.spring_chat.contorller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viking.spring_chat.Entity.User;
import com.viking.spring_chat.dto.ApiResponse;
import com.viking.spring_chat.dto.JwtResponse;
import com.viking.spring_chat.dto.LoginRequest;
import com.viking.spring_chat.dto.RegistrationRequest;
import com.viking.spring_chat.dto.UserResponse;
import com.viking.spring_chat.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody RegistrationRequest request) {
        User savedUser = userService.registerNewUser(request);
        UserResponse userResponse = new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
        ApiResponse<UserResponse> response = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Пользователь успешно зарегистрирован",
            userResponse,
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.authenticateAndGetToken(loginRequest);
        ApiResponse<JwtResponse> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Аутентификация успешна",
            new JwtResponse(token),
            LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

}
