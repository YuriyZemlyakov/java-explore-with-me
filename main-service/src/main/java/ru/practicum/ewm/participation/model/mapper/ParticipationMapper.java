package ru.practicum.ewm.participation.model.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.participation.model.Participation;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

@Mapper
public interface ParticipationMapper {
    Participation dtoToEntity(ParticipationRequestDto dto);

    ParticipationRequestDto entityToDto(Participation participation);
}
