package ru.practicum.ewm.participation;

import ru.practicum.ewm.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

public interface ParticipationService {
    Collection<ParticipationRequestDto> getUserRequests(long userId);
    ParticipationRequestDto addParticipationRequest(long userId, long eventId);
    ParticipationRequestDto cancelRequest(long userId, long requestId);



}
