package ru.practicum.ewm.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {
    @NotBlank
    private long eventId;
    @NotBlank
    private long authorId;
    @NotBlank
    @Size(min = 2, max = 250)
    private String text;
}