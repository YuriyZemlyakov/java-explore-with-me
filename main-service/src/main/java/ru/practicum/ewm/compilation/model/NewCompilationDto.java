package ru.practicum.ewm.compilation.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class NewCompilationDto {
    private Set<Long> events;
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    private boolean pinned;
}
