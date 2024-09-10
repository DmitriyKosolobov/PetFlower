package ru.petflower.exception;

public class ExistUserException extends RuntimeException {
    public ExistUserException() {
        super("User already exists");
    }
}
