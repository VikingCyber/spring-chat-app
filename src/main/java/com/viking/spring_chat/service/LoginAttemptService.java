package com.viking.spring_chat.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {
    private final StringRedisTemplate redisTemplate;
    private static final String ATTEMPT_PREFIX = "login_attempts:";
    private static final int MAX_ATTEMPTS = 5;
    private static final Duration ATTEMPT_EXPIRE = Duration.ofMinutes(15);

    public LoginAttemptService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void loginSucceeded(String email) {
        redisTemplate.delete(ATTEMPT_PREFIX + email);
    }

    public void loginFailed(String email) {
        String key = ATTEMPT_PREFIX + email;
        Long attempts = redisTemplate.opsForValue().increment(key);
        if (attempts != null && attempts == 1) {
            redisTemplate.expire(key, ATTEMPT_EXPIRE);
        }
    }

    public boolean isBlocked(String email) {
        String key = ATTEMPT_PREFIX + email;
        String attemptsStr = redisTemplate.opsForValue().get(key);
        int attempts = attemptsStr == null ? 0 : Integer.parseInt(attemptsStr);
        return attempts >= MAX_ATTEMPTS;
    }
}
