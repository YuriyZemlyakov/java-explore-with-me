package ru.practicum.ewm.user;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserShortDto dto);

    UserDto entityToDto(User user);
}
