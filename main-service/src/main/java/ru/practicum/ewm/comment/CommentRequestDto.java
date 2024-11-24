package ru.practicum.ewm.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {
    private long eventId;
    @NotBlank
    @Size(min = 5, max = 250)
    private String text;
}
