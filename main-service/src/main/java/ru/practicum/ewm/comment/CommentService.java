package ru.practicum.ewm.comment;

import java.util.Collection;

public interface CommentService {
    CommentResponseDto addComment(long userId, long eventId, CommentRequestDto dto);

    CommentResponseDto editComment(long commentId, long userId, CommentRequestDto dto);

    CommentResponseDto getComment(long commentId);

    Collection<CommentResponseDto> getEventComments(long eventId);

    void deleteComment(long commentId);
}
