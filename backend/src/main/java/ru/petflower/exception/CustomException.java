package ru.petflower.exception;

public class CustomException extends RuntimeException {
    private final ErrorType errorType;

    public CustomException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CustomException(ErrorType errorType, String additionalMessage) {
        super(errorType.getMessage() + ": " + additionalMessage);
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorType.getCode();
    }
}
