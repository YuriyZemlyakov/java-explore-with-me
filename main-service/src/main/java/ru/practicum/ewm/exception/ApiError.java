package ru.practicum.ewm.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp;
}
