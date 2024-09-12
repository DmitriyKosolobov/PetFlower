package ru.petflower.exception;

public enum ErrorType {
    AUTH_EXCEPTION(401, "Unauthorized"),
    REGISTRATION_EXCEPTION(409, "Registration error"),
    NOT_FOUND_EXCEPTION(404, "Not found"),
    ALREADY_EXIST_EXCEPTION(409, "Already exist");

    private final int code;
    private final String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

