package ru.petflower.exception;

public class ExistDeviceException extends RuntimeException {
    public ExistDeviceException() {
        super("Device already exists");
    }
}
