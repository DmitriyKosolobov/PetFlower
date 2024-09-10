package ru.petflower.controller.responses.userAccount;

public record PutUserAccountRequest (
        String username,
        String email,
        String info
) {
}