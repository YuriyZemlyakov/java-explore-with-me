package ru.practicum.ewmstatserver.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmDto.Hit;
import ru.practicum.ewmDto.HitDto;


@Mapper
public interface HitMapper {
    HitDto entityToDto(Hit hit);

    Hit dtoToEntity(HitDto dto);
}
