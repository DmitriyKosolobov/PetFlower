package ru.petflower.controller.dto;

public record JwtLoginRequest(
    String login,
    String password
) {}