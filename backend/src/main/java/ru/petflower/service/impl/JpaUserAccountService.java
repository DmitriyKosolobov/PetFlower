package ru.petflower.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petflower.controller.responses.userAccount.PutUserAccountRequest;
import ru.petflower.controller.responses.userAccount.UserAccountResponse;
import ru.petflower.domain.JpaUserAccountInfoRepository;
import ru.petflower.domain.JpaUserAccountRepository;
import ru.petflower.domain.entity.UserAccount;
import ru.petflower.domain.entity.UserAccountInfo;
import ru.petflower.domain.jwt.Role;
import ru.petflower.domain.jwt.User;
import ru.petflower.exception.CustomException;
import ru.petflower.exception.ErrorType;
import ru.petflower.service.UserAccountService;
import ru.petflower.service.jwt.AuthService;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserAccountService implements UserAccountService {

    private final JpaUserAccountRepository jpaUserAccountRepository;
    private final JpaUserAccountInfoRepository jpaUserAccountInfoRepository;

    @Override
    @Transactional
    public User register(String username, String email, String password, String info) {
        UserAccount userAccount = new UserAccount(username, email, password);
        UserAccountInfo userAccountInfo = new UserAccountInfo(info);
        userAccount.addUserAccountInfo(userAccountInfo);
        jpaUserAccountInfoRepository.save(userAccountInfo);
        jpaUserAccountRepository.save(userAccount);
        return new User(
                userAccount.getId(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                null,
                null,
                userAccount.getEmail(),
                Collections.singleton(Role.USER)
        );
    }

    @Override
    @Transactional
    public UserAccountResponse unregister(Long userId) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findById(userId);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }
        UserAccount userAccount = optionalUserAccount.get();
        UserAccountInfo userAccountInfo = userAccount.getUserAccountInfo();
        UserAccountResponse response = new UserAccountResponse(
                userAccount.getId(),
                userAccount.getUsername(),
                userAccount.getEmail(),
                userAccountInfo.getInfo()
        );
        jpaUserAccountInfoRepository.delete(userAccountInfo);
        jpaUserAccountRepository.delete(userAccount);
        return response;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByUsername(login);
        return convertUserAccountToUser(optionalUserAccount);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findByEmail(email);
        return convertUserAccountToUser(optionalUserAccount);
    }

    @Override
    public UserAccountResponse getUserInfo(Long userId) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findById(userId);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }
        UserAccount userAccount = optionalUserAccount.get();
        UserAccountInfo userAccountInfo = userAccount.getUserAccountInfo();
        return new UserAccountResponse(
                userAccount.getId(),
                userAccount.getUsername(),
                userAccount.getEmail(),
                userAccountInfo.getInfo()
        );
    }

    @Override
    @Transactional
    public User changeUserInfo(Long userId, PutUserAccountRequest request) {
        Optional<UserAccount> optionalUserAccount = jpaUserAccountRepository.findById(userId);
        if(optionalUserAccount.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_EXCEPTION, "Пользователь не найден");
        }
        UserAccount userAccount = optionalUserAccount.get();
        String newUsername = request.username();
        String oldUsername = userAccount.getUsername();
        if(!newUsername.equals(oldUsername)) {
            boolean isUsernameExist = jpaUserAccountRepository.existsByUsername(newUsername);
            if(isUsernameExist) {
                throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Данное имя пользователя уже занято");
            }
            userAccount.setUsername(newUsername);
            AuthService.removeTokenFromRefreshStorage(oldUsername);
        }

        String newEmail = request.email();
        if(!newEmail.equals(userAccount.getEmail())) {
            boolean isEmailExist = jpaUserAccountRepository.existsByEmail(newEmail);
            if(isEmailExist) {
                throw new CustomException(ErrorType.ALREADY_EXIST_EXCEPTION, "Данная почта уже привязана к другому аккаунту");
            }
            userAccount.setEmail(newEmail);
        }

        UserAccountInfo userAccountInfo = userAccount.getUserAccountInfo();
        if(!request.info().equals(userAccountInfo.getInfo())) {
            userAccountInfo.setInfo(request.info());
        }

        jpaUserAccountRepository.save(userAccount);
        jpaUserAccountInfoRepository.save(userAccountInfo);
        return convertUserAccountToUser(Optional.of(userAccount)).get();
    }

    private Optional<User> convertUserAccountToUser(Optional<UserAccount> optionalUserAccount) {
        if(optionalUserAccount.isEmpty()) {
            return Optional.empty();
        } else {
            UserAccount userAccount = optionalUserAccount.get();
            return Optional.of(new User(
                    userAccount.getId(),
                    userAccount.getUsername(),
                    userAccount.getPassword(),
                    null, null,
                    userAccount.getEmail(),
                    Collections.singleton(Role.USER)
            ));
        }
    }

}
