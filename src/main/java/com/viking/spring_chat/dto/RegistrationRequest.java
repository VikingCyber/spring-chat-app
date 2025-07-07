package com.viking.spring_chat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest (
    @NotNull @Size(min = 3, max = 100) String username,
    @NotNull @Size(min = 8, max = 255) String password,
    @NotNull @Email @Size(max = 100) String email
 ) {}
