package com.viking.spring_chat.config;

import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.viking.spring_chat.exception.WebSocketAuthnticationException;
import com.viking.spring_chat.security.StompPrincipal;
import com.viking.spring_chat.security.jwt.JwtUtil;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor{
    
    private final JwtUtil jwtUtil;

    public AuthChannelInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new WebSocketAuthnticationException("No valid Authorization header");
            }
            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                throw new WebSocketAuthnticationException("Invalid JWT token");
            }
            String username = jwtUtil.getUsernameFromToken(token);
            accessor.setUser(new StompPrincipal(username));
        }
        return message;
    }
}
