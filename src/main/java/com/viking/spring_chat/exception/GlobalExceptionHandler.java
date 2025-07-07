package com.viking.spring_chat.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.viking.spring_chat.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 401
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Неверный логин или пароль", request);
    }

    // 429
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse> handleLockedException(LockedException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.TOO_MANY_REQUESTS, ex.getMessage(), request);
    }

    // 401
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Неверный логин или пароль", request);
    }

    // 409
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // 409
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StringBuilder errors = new StringBuilder("Ошибка валидации: ");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getField())
                .append(" - ")
                .append(error.getDefaultMessage())
                .append("; ");
        }
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, errors.toString(), request);
    }

    // 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "Доступ запрещен", request);
    }

    // 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка сервера:" + ex.getMessage(), request);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Internal server error occured", ex);
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка сервера: " + ex.getMessage(), request);
    }   

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
            status.value(), 
            status.getReasonPhrase(),
            message,
            request.getRequestURI(),

            LocalDateTime.now()
            );
        return ResponseEntity.status(status).body(response);               
    }
}
