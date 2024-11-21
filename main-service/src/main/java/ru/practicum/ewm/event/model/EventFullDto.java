package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.user.UserShortDto;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    private long id;
    @NotBlank
    private String annotation;
    @NotBlank
    private Category category;
    private long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotBlank
    private UserShortDto initiator;
    @NotBlank
    private Location location;
    @NotBlank
    private boolean paid;
    private long participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private StateEvent state;
    @NotBlank
    private String title;
    private long views;
}

