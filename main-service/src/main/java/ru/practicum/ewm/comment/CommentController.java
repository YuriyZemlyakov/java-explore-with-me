package ru.practicum.ewm.comment;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService service;

    @PostMapping
    public CommentResponseDto addComment(@RequestParam long userId,
                                         @RequestParam long eventId,
                                         @RequestBody @Valid CommentRequestDto dto) {
        return service.addComment(userId, eventId, dto);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto editComment(@PathVariable long commentId,
                                          @RequestParam long userId,
                                          @RequestBody @Valid CommentRequestDto dto) {
        return service.editComment(commentId, userId, dto);
    }

    @GetMapping("/{commentId}")
    public CommentResponseDto getComment(@PathVariable long commentId) {
        return service.getComment(commentId);
    }

    @GetMapping
    public Collection<CommentResponseDto> getEventComments(@RequestParam long eventId) {
        return service.getEventComments(eventId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        service.deleteComment(commentId);
    }
}
