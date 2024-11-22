package ru.practicum.ewm.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "event.title", target = "eventTitle")
    @Mapping(source = "author.name", target = "authorName")
    CommentResponseDto entityToDto(Comment comment);
}
