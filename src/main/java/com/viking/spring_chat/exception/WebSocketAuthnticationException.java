package com.viking.spring_chat.exception;

public class WebSocketAuthnticationException extends RuntimeException {
    public WebSocketAuthnticationException(String message) {
        super(message);
    }
}
