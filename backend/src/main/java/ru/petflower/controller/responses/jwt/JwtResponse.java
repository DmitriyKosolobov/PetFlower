package ru.petflower.controller.responses.jwt;

public record JwtResponse (
    String type,
    String accessToken,
    String refreshToken

) {}