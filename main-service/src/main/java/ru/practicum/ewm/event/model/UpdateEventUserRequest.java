package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000)
    private String annotation;
    private long category;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    @PositiveOrZero
    private long participantLimit;
    private boolean requestModeration;
    private StateActionUser stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
