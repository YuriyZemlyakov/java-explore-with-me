package ru.practicum.ewm.event.privateEvents;

import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

public interface EventPrivateService {
    Collection<EventShortDto> getEvents(long userId, int from, int size);
    EventFullDto addEvent(long userId, NewEventDto newEventDto);
    EventFullDto getEvent(long userId, long eventId);
    EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);
    Collection<ParticipationRequestDto> getEventParticipants(long userId, long eventId);
    EventRequestStatusUpdateResult changeRequestStatus(long userId, long eventId,
                                                       EventRequestStatusUpdateRequest request);





}
