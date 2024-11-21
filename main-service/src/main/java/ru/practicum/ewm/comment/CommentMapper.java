package ru.practicum.ewm.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(source = )
    Comment dtoToEnity(CommentRequestDto dto);
}
