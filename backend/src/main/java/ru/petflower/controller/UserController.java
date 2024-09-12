package ru.petflower.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.responses.jwt.JwtResponse;
import ru.petflower.controller.responses.userAccount.PutUserAccountRequest;
import ru.petflower.controller.responses.userAccount.UserAccountResponse;
import ru.petflower.service.UserAccountService;
import ru.petflower.service.jwt.AuthService;

@RestController
@RequiredArgsConstructor
public class UserController {
    public static final String API_PREFIX = "user";
    public static final String GET_USER = API_PREFIX + "/{user_id}";
    public static final String PUT_USER = API_PREFIX + "/{user_id}";
    public static final String DELETE_USER = API_PREFIX + "/{user_id}";

    private final UserAccountService userAccountService;
    private final AuthService authService;

    @GetMapping(GET_USER)
    public ResponseEntity<UserAccountResponse> getUserAccountInfo(@PathVariable("user_id") Long userId) {
        UserAccountResponse response = userAccountService.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(PUT_USER)
    public ResponseEntity<JwtResponse> changeUserAccountInfo(@PathVariable("user_id") Long userId, @RequestBody PutUserAccountRequest request) {
        JwtResponse response = authService.changeUserInfo(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity<UserAccountResponse> deleteUserAccount(@PathVariable("user_id") Long userId) {
        UserAccountResponse response = authService.unregister(userId);
        return ResponseEntity.ok(response);
    }

}
