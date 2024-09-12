package ru.petflower.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.petflower.controller.requests.jwt.JwtRegisterRequest;
import ru.petflower.service.jwt.AuthService;
import ru.petflower.controller.requests.jwt.JwtLoginRequest;
import ru.petflower.controller.responses.jwt.JwtResponse;
import ru.petflower.controller.requests.jwt.RefreshJwtRequest;

@RestController
public class AuthController {
    public static final String API_PREFIX = "auth/";
    public static final String LOGIN = API_PREFIX + "login";
    public static final String REGISTER = API_PREFIX + "register";
    public static final String GET_NEW_ACCESS_TOKEN = API_PREFIX + "token";
    public static final String GET_NEW_REFRESH_TOKEN = API_PREFIX + "refresh";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(LOGIN)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtLoginRequest loginRequest) {
        final JwtResponse token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping(REGISTER)
    public ResponseEntity<JwtResponse> register(@RequestBody @Valid JwtRegisterRequest registerRequest) {
        final JwtResponse token = authService.register(registerRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping(GET_NEW_ACCESS_TOKEN)
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping(GET_NEW_REFRESH_TOKEN)
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(token);
    }

}