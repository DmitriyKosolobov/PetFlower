package ru.petflower.service;

import ru.petflower.domain.jwt.User;

import java.util.Optional;

public interface UserAccountService {
    User register(String username, String email, String password, String info);
    void unregister();
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);
}
