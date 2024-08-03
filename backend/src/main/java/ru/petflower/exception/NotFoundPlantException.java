package ru.petflower.exception;

public class NotFoundPlantException extends RuntimeException {
    public NotFoundPlantException() {
        super("Plant was not found");
    }
}