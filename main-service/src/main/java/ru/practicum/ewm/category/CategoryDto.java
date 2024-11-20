package ru.practicum.ewm.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    private long id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}