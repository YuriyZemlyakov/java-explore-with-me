package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentResponseDto;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.CommentUpdateDto;

@RestController
@RequestMapping("/admin/comments")
@AllArgsConstructor
public class AdminCommentController {
    private final CommentService service;

    @PatchMapping("/{commentId}")
    public CommentResponseDto moderateComment(@PathVariable long commentId,
                                              @RequestBody @Valid CommentUpdateDto dto) {
        return service.moderateComment(commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        service.deleteCommentAdmin(commentId);
    }
}
