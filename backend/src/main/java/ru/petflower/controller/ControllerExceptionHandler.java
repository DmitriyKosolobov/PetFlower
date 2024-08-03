package ru.petflower.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.petflower.controller.dto.ErrorResponse;
import ru.petflower.exception.ExistDeviceException;
import ru.petflower.exception.NotFoundDeviceException;
import ru.petflower.exception.NotFoundPlantException;
import ru.petflower.exception.NotFoundUserException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ExistDeviceException.class)
    public ResponseEntity<?> existDeviceHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Устройство уже зарегистрировано", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundDeviceException.class)
    public ResponseEntity<?> notFoundDeviceHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Устройство не зарегистрировано", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> notFoundUserHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Пользователь не зарегистрирован", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundPlantException.class)
    public ResponseEntity<?> notFoundPlantHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Растение не зарегистрировано", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createErrorResponse(Exception ex, String message, HttpStatus httpStatus) {
        return new ErrorResponse(
                String.valueOf(httpStatus.value()),
                message,
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
    }
}