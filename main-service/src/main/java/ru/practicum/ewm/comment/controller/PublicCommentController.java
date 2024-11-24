package ru.practicum.ewm.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentResponseDto;
import ru.practicum.ewm.comment.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class PublicCommentController {
    private final CommentService service;

    @GetMapping("/{commentId}")
    public CommentResponseDto getComment(@PathVariable long commentId) {
        return service.getComment(commentId);
    }

    @GetMapping
    public Collection<CommentResponseDto> getEventComments(@RequestParam long eventId) {
        return service.getEventComments(eventId);
    }
}
