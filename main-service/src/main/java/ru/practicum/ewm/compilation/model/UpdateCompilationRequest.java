package ru.practicum.ewm.compilation.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.ewm.event.model.EventShortDto;

import java.util.Set;

@Data
public class UpdateCompilationRequest {
    private Set<EventShortDto> events;
    @Size(min = 1, max = 50)
    private String title;
    private boolean pinned;
}
