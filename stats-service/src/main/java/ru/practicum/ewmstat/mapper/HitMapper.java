package ru.practicum.ewmstat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmstat.model.Hit;
import ru.practicum.ewmstat.model.dto.HitDto;

@Mapper
public interface HitMapper {
    HitDto entityToDto(Hit hit);
    Hit dtoToEntity(HitDto dto);
}
