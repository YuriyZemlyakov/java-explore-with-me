package ru.practicum.ewm.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserShortDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}
