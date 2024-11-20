package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.user.UserShortDto;

import java.time.LocalDateTime;

@Data
public class EventShortDto {
    private long id;
    private String annotation;
    private Category category;
    private long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;
}
