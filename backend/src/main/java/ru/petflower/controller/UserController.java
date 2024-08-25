package ru.petflower.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petflower.controller.responses.userAccount.PutUserAccountRequest;
import ru.petflower.controller.responses.userAccount.UserAccountResponse;

@RestController
public class UserController {
    public static final String API_PREFIX = "user/";
    public static final String GET_USER = API_PREFIX + "{user_id}";
    public static final String PUT_USER = API_PREFIX + "{user_id}";
    public static final String DELETE_USER = API_PREFIX + "{user_id}";

    @GetMapping(GET_USER)
    public ResponseEntity<UserAccountResponse> getUserAccountInfo(@PathVariable("user_id") Long userId) {
        //TODO
        UserAccountResponse response = null;
        return ResponseEntity.ok(response);
    }

    @PutMapping(PUT_USER)
    public ResponseEntity<UserAccountResponse> changeUserAccountInfo(@PathVariable("user_id") Long userId, @RequestBody PutUserAccountRequest request) {
        //TODO
        UserAccountResponse response = null;
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity<UserAccountResponse> deleteUserAccount(@PathVariable("user_id") Long userId) {
        //TODO
        UserAccountResponse response = null;
        return ResponseEntity.ok(response);
    }

}
