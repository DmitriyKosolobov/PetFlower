package ru.petflower.service;

import ru.petflower.controller.responses.userAccount.PutUserAccountRequest;
import ru.petflower.controller.responses.userAccount.UserAccountResponse;
import ru.petflower.domain.jwt.User;

import java.util.Optional;

public interface UserAccountService {
    User register(String username, String email, String password, String info);
    UserAccountResponse unregister(Long userId);
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);
    UserAccountResponse getUserInfo(Long userId);
    User changeUserInfo(Long userId, PutUserAccountRequest request);
}
