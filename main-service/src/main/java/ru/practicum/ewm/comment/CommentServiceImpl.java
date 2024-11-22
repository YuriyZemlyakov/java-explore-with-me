package ru.practicum.ewm.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final EventStorage eventStorage;
    private final CommentMapper mapper;
    private final CommentStorage storage;
    private final UserStorage userStorage;

    @Override
    public CommentResponseDto addComment(long userId, long eventId, CommentRequestDto dto) {
        Event event = eventStorage.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
        User author = userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %s не найден", userId)));
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setText(dto.getText());
        comment.setCreatedAt(LocalDateTime.now());
        return mapper.entityToDto(storage.save(comment));
    }

    @Override
    public CommentResponseDto editComment(long commentId, long userId, CommentRequestDto dto) {
        Comment comment = storage.findByIdAndAuthor_Id(commentId, userId)
                .orElseThrow(() -> new ConflictException(String.format("У пользователя %s не найдено комментариев" +
                        "с id %s", userId, commentId)));
        comment.setText(dto.getText());
        comment.setLastModified(LocalDateTime.now());
        return mapper.entityToDto(storage.save(comment));
    }

    @Override
    public CommentResponseDto getComment(long commentId) {
        Comment comment = storage.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", commentId)));
        return mapper.entityToDto(comment);
    }

    @Override
    public Collection<CommentResponseDto> getEventComments(long eventId) {
        return storage.findByEvent_Id(eventId).stream()
                .map(comment -> mapper.entityToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(long commentId) {
        storage.deleteById(commentId);
    }
}
