package ru.petflower.service.jwt;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.petflower.controller.requests.jwt.JwtLoginRequest;
import ru.petflower.controller.requests.jwt.JwtRegisterRequest;
import ru.petflower.controller.responses.jwt.JwtResponse;
import ru.petflower.controller.responses.userAccount.PutUserAccountRequest;
import ru.petflower.controller.responses.userAccount.UserAccountResponse;
import ru.petflower.domain.jwt.JwtAuthentication;
import ru.petflower.domain.jwt.Role;
import ru.petflower.domain.jwt.User;
import ru.petflower.exception.CustomException;
import ru.petflower.exception.ErrorType;
import ru.petflower.service.UserAccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountService userAccountService;

    //TODO : заменить на БД
    public static final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtLoginRequest loginRequest) {
        final User user = userAccountService.findUserByLogin(loginRequest.login())
                .orElseThrow(() -> new CustomException(ErrorType.AUTH_EXCEPTION, "Пользователь не найден"));
        if (BCrypt.checkpw(loginRequest.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponse("Bearer", accessToken, refreshToken);
        } else {
            throw new CustomException(ErrorType.AUTH_EXCEPTION, "Неправильный пароль");
        }
    }

    public JwtResponse register(@NonNull JwtRegisterRequest registerRequest) {
        Optional<User> optionalUserByLogin = userAccountService.findUserByLogin(registerRequest.login());
        Optional<User> optionalUserByEmail = userAccountService.findUserByEmail(registerRequest.login());
        if(optionalUserByLogin.isPresent()) {
            throw new CustomException(ErrorType.REGISTRATION_EXCEPTION, "Пользователь c данным именем уже зарегистрирован");
        } else if (optionalUserByEmail.isPresent()) {
            throw new CustomException(ErrorType.REGISTRATION_EXCEPTION, "Пользователь c данным email уже зарегистрирован");
        }
        String hashedPassword = BCrypt.hashpw(registerRequest.password(), BCrypt.gensalt());
        User user = userAccountService.register(registerRequest.login(), registerRequest.email(), hashedPassword, null);
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getLogin(), refreshToken);
        return new JwtResponse("Bearer", accessToken, refreshToken);
    }

    public UserAccountResponse unregister(@NonNull Long userId) {
        JwtAuthentication jwtAuthentication = this.getAuthInfo();
        boolean isAdmin = jwtAuthentication.getRoles().contains(Role.ADMIN);
        if(!jwtAuthentication.getUserId().equals(userId) && !isAdmin) {
            throw new CustomException(ErrorType.AUTH_EXCEPTION, "Недостаточно прав для удаления данного пользователя");
        }
        UserAccountResponse response = userAccountService.unregister(userId);
        refreshStorage.remove(response.username());
        return response;
    }

    public JwtResponse changeUserInfo(Long userId, PutUserAccountRequest request) {
        JwtAuthentication jwtAuthentication = this.getAuthInfo();
        boolean isAdmin = jwtAuthentication.getRoles().contains(Role.ADMIN);
        if(!jwtAuthentication.getUserId().equals(userId) && !isAdmin) {
            throw new CustomException(ErrorType.AUTH_EXCEPTION, "Недостаточно прав для изменения информации данного пользователя");
        }
        User user = userAccountService.changeUserInfo(userId, request);
        String newAccessToken = jwtProvider.generateAccessToken(user);
        String newRefreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getLogin(), newRefreshToken);
        return new JwtResponse("Bearer", newAccessToken, newRefreshToken);
    }

    public static void removeTokenFromRefreshStorage(String login) {
        refreshStorage.remove(login);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userAccountService.findUserByLogin(login)
                        .orElseThrow(() -> new CustomException(ErrorType.AUTH_EXCEPTION, "Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse("Bearer", accessToken, null);
            }
        }
        return new JwtResponse("Bearer", null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userAccountService.findUserByLogin(login)
                        .orElseThrow(() -> new CustomException(ErrorType.AUTH_EXCEPTION, "Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponse("Bearer", accessToken, newRefreshToken);
            }
        }
        throw new CustomException(ErrorType.AUTH_EXCEPTION, "Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public void setAuthInfo(JwtAuthentication jwtAuthentication) {
        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
    }

}