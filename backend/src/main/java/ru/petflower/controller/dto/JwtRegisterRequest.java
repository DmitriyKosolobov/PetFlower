package ru.petflower.controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record JwtRegisterRequest(
        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
        String login,
        @Email(message = "Email should be valid")
        String email,
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {}
