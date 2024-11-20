package ru.practicum.ewm.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice("ru.practicum.ewm")
public class ApiErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        ApiError apiError= ApiError.builder()
                .message(e.getMessage())
                .reason("Объект не найден")
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, apiError.status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleConflictException(ConflictException e) {
        ApiError apiError = ApiError.builder()
                .message(e.getMessage())
                .reason("Объект находится в ненадлежащем состоянии")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, apiError.status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
         ApiError apirError = ApiError.builder()
                .message(e.getMessage())
                .reason("Ошика в переданных параметрах")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
         return new ResponseEntity<>(apirError,apirError.status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleValidationException(ValidationException e) {
        ApiError apirError = ApiError.builder()
                .message(e.getMessage())
                .reason("Ошика валидации")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apirError,apirError.status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleDataIntegrityViolationException (DataIntegrityViolationException e) {
        ApiError apirError = ApiError.builder()
                .message(e.getMessage())
                .reason("Попытка внести дубликат")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apirError, apirError.status);
    }
}
