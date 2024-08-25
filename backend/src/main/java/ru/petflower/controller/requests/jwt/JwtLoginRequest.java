package ru.petflower.controller.requests.jwt;

public record JwtLoginRequest(
    String login,
    String password
) {}