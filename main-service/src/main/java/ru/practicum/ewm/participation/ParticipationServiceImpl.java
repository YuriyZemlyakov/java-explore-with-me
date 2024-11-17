package ru.practicum.ewm.participation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;
import ru.practicum.ewm.participation.model.mapper.ParticipationMapper;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {
    private final ParticipationMapper mapper;
    private final ParticipationStorage storage;

    @Override
    public Collection<ParticipationRequestDto> getUserRequests(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long eventId) {
        return null;
    }
}
