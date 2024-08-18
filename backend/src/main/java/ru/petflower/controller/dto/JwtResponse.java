package ru.petflower.controller.dto;

public record JwtResponse (
    String type,
    String accessToken,
    String refreshToken

) {}