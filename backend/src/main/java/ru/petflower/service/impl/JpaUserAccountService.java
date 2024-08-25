package ru.petflower.service.impl;

import org.springframework.stereotype.Service;
import ru.petflower.domain.JpaUserAccountInfoRepository;
import ru.petflower.domain.JpaUserAccountRepository;
import ru.petflower.domain.entity.UserAccount;
import ru.petflower.domain.entity.UserAccountInfo;
import ru.petflower.domain.jwt.Role;
import ru.petflower.domain.jwt.User;
import ru.petflower.service.UserAccountService;

import java.util.Collections;
import java.util.Optional;

@Service
public class JpaUserAccountService implements UserAccountService {

    private final JpaUserAccountRepository jpaUserAccountRepository;
    private final JpaUserAccountInfoRepository jpaUserAccountInfoRepository;

    public JpaUserAccountService(JpaUserAccountRepository jpaUserAccountRepository,
                                 JpaUserAccountInfoRepository jpaUserAccountInfoRepository) {
        this.jpaUserAccountRepository = jpaUserAccountRepository;
        this.jpaUserAccountInfoRepository = jpaUserAccountInfoRepository;
    }

    @Override
    public User register(String username, String email, String password, String info) {
        UserAccount userAccount = new UserAccount(username, email, password);
        UserAccountInfo userAccountInfo = new UserAccountInfo(info);
        userAccount.addUserAccountInfo(userAccountInfo);
        jpaUserAccountInfoRepository.save(userAccountInfo);
        jpaUserAccountRepository.save(userAccount);
        return new User(username,
                password,
                null,
                null,
                email,
                Collections.singleton(Role.USER)
        );
    }

    @Override
    public void unregister() {

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

    private Optional<User> convertUserAccountToUser(Optional<UserAccount> optionalUserAccount) {
        if(optionalUserAccount.isEmpty()) {
            return Optional.empty();
        } else {
            UserAccount userAccount = optionalUserAccount.get();
            return Optional.of(new User(
                    userAccount.getUsername(),
                    userAccount.getPassword(),
                    null, null,
                    userAccount.getEmail(),
                    Collections.singleton(Role.USER)
            ));
        }
    }

}
