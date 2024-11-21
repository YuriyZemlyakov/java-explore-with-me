package ru.practicum.ewm.compilation.model;

import lombok.Data;
import ru.practicum.ewm.event.model.EventShortDto;

import java.util.Set;

@Data
public class CompilationDto {
    private long id;
    private Set<EventShortDto> events;
    private String title;
    private boolean pinned;
}
