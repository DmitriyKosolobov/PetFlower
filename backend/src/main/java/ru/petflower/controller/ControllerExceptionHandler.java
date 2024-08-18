package ru.petflower.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.petflower.controller.dto.ErrorResponse;
import ru.petflower.exception.*;

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

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authExceptionHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Ошибка аутентификации", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExistUserException.class)
    public ResponseEntity<?> existUserHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Пользователь уже зарегистрирован", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argNotValidHandler(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, "Некорректные данные в запросе", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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