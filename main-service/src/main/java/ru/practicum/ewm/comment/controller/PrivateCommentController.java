package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentRequestDto;
import ru.practicum.ewm.comment.CommentResponseDto;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.CommentUpdateDto;

@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/comments")
public class PrivateCommentController {
    private final CommentService service;

    @PostMapping
    public CommentResponseDto addComment(@PathVariable long userId,
                                         @RequestBody @Valid CommentRequestDto dto) {
        return service.addComment(userId, dto);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto editComment(@PathVariable long commentId,
                                          @PathVariable long userId,
                                          @RequestBody @Valid CommentUpdateDto dto) {
        return service.editComment(commentId, userId, dto);
    }


    @DeleteMapping("/{commentId}")
    public void deleteCommentUser(@PathVariable long userId,
                                  @PathVariable long commentId) {
        service.deleteCommentUser(userId, commentId);
    }
}
