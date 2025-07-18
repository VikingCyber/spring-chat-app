package com.viking.spring_chat.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viking.spring_chat.dto.JwtResponse;
import com.viking.spring_chat.dto.LoginRequest;
import com.viking.spring_chat.dto.RegistrationRequest;
import com.viking.spring_chat.entity.User;
import com.viking.spring_chat.exception.EmailAlreadyExistsException;
import com.viking.spring_chat.exception.UsernameAlreadyExistsException;
import com.viking.spring_chat.mapper.UserMapper;
import com.viking.spring_chat.repository.UserRepository;
import com.viking.spring_chat.security.CustomUserDetails;
import com.viking.spring_chat.security.CustomUserDetailsService;
import com.viking.spring_chat.security.jwt.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                    CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil,
                    LoginAttemptService loginAttemptService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.loginAttemptService = loginAttemptService;
        this.userMapper = userMapper;
    }

    public User registerNewUser(RegistrationRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Пользователь с email " + request.email() + " уже существует");
        }
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UsernameAlreadyExistsException("Пользователь с username " + request.username() + " уже существует");
        }
        User user = userMapper.toEntity(request, passwordEncoder);
        return userRepository.save(user);
    }

    public JwtResponse authenticateAndGetToken(LoginRequest loginRequest) {
        String email = loginRequest.email();

        if (loginAttemptService.isBlocked(email)) {
            log.warn("Пользователь {} заблокирован из-за слишком большого числа попыток входа", email);
            throw new LockedException("Слишком много неудачных попыток входа, попробуйте позже");
        }

        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if (!passwordEncoder.matches(loginRequest.password(), userDetails.getPassword())) {
                log.warn("Неудачная попытка входа: неверный пароль для email {}", email);
                loginAttemptService.loginFailed(email);
                throw new BadCredentialsException("Неверный пароль");
            }

            log.info("Пользователь {} успешно вошёл", email);
            loginAttemptService.loginSucceeded(email);
            String token = jwtUtil.generateToken(userDetails);
            String username = ((CustomUserDetails) userDetails).getUser().getUsername(); 
            return new JwtResponse(token, username);

        } catch (UsernameNotFoundException ex) {
            log.warn("Неудачная попытка входа: пользователь с email {} не найден", email);
            loginAttemptService.loginFailed(email);
            throw new BadCredentialsException("Неверный логин или пароль");
        }
        
    }
}
