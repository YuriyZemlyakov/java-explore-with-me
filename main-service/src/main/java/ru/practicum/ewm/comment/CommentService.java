package ru.practicum.ewm.comment;

import java.util.Collection;

public interface CommentService {
    CommentResponseDto addComment(long userId, CommentRequestDto dto);

    CommentResponseDto editComment(long commentId, long userId, CommentUpdateDto dto);

    CommentResponseDto moderateComment(long commentId, CommentUpdateDto dto);

    CommentResponseDto getComment(long commentId);

    Collection<CommentResponseDto> getEventComments(long eventId);

    void deleteCommentAdmin(long commentId);

    void deleteCommentUser(long userId, long commentId);
}
