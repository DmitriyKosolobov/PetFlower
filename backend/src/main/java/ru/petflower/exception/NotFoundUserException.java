package ru.petflower.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("User was not found");
    }
}
