package com.viking.spring_chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatRoomTypeNotFoundException extends RuntimeException{
    public ChatRoomTypeNotFoundException(String message) {
        super(message);
    }
}
