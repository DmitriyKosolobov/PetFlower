package ru.petflower.controller.responses.userAccount;

public record UserAccountResponse (
    Long userId,
    String username,
    String email,
    String info
) {
}