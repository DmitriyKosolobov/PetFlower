package ru.petflower.exception;

public class NotFoundDeviceException extends RuntimeException {
    public NotFoundDeviceException() {
        super("Device was not found");
    }
}
