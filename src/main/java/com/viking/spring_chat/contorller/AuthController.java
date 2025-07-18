package com.viking.spring_chat.contorller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viking.spring_chat.dto.ApiResponse;
import com.viking.spring_chat.dto.ErrorResponse;
import com.viking.spring_chat.dto.JwtResponse;
import com.viking.spring_chat.dto.LoginRequest;
import com.viking.spring_chat.dto.RegistrationRequest;
import com.viking.spring_chat.dto.UserResponse;
import com.viking.spring_chat.entity.User;
import com.viking.spring_chat.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @Operation(
        summary = "Регистрация нового пользователя",
        description = "Создает нового пользователя и возвращает его данные.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegistrationRequest.class),
                examples = @ExampleObject(
                    value = """
                    {
                    "username": "newuser",
                    "email": "newuser@example.com",
                    "password": "strongpassword123"
                    }
                    """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Пользователь успешно зарегистрирован",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                        value = """
                        {
                        "status": 201,
                        "message": "Пользователь успешно зарегистрирован",
                        "data": {
                            "id": 42,
                            "username": "newuser",
                            "email": "newuser@example.com"
                        },
                        "timestamp": "2025-07-17T22:15:30.123456"
                        }
                        """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Ошибка валидации данных",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                        value = """
                        {
                        "status": 400,
                        "error": "Bad Request",
                        "message": "Email уже используется",
                        "path": "/api/auth/register",
                        "timestamp": "2025-07-17T22:16:00.000000"
                        }
                        """
                    )
                )
            )
        }
    )
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


    @Operation(
        summary = "Аутентификация пользователя",
        description = "Выполняет вход пользователя и возвращает JWT-токен.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class),
                examples = @ExampleObject(
                    value = """
                    {
                    "email": "viking@example.com",
                    "password": "vikingpassword123"
                    }
                    """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Аутентификация успешна",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                        value = """
                        {
                        "status": 200,
                        "message": "Аутентификация успешна",
                        "data": {
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                            "type": "Bearer"
                        },
                        "timestamp": "2025-07-17T22:20:45.000000"
                        }
                        """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "401",
                description = "Неверные учетные данные",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                        value = """
                        {
                        "status": 401,
                        "error": "Unauthorized",
                        "message": "Неверный email или пароль",
                        "path": "/api/auth/login",
                        "timestamp": "2025-07-17T22:21:10.000000"
                        }
                        """
                    )
                )
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.authenticateAndGetToken(loginRequest);
        ApiResponse<JwtResponse> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Аутентификация успешна",
            jwtResponse,
            LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

}
