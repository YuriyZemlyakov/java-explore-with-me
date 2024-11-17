package ru.practicum.ewm.event.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.ewm.category.Category;

import java.time.LocalDateTime;
@Data
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Category category;
    @Size(min = 20, max = 7000)
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private long participantLimit;
    private boolean requestModeration;
    private StateActionAdmin stateAction;
    @Size(min = 20, max = 120)
    private String title;
}
