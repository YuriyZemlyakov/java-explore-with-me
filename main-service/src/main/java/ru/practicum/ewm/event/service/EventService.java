package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.publicEvents.SortType;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {
    Collection<EventShortDto> getEvents(long userId, int from, int size);

    EventFullDto addEvent(long userId, NewEventDto newEventDto);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);

    Collection<ParticipationRequestDto> getEventParticipants(long userId, long eventId);

    EventRequestStatusUpdateResult changeRequestStatus(long userId, long eventId,
                                                       EventRequestStatusUpdateRequest request);

    Collection<EventFullDto> getEvents(Collection<Long> users,
                                       Collection<String> states,
                                       Collection<Long> categories,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       long from,
                                       long size);

    EventFullDto editEvent(long eventId, UpdateEventAdminRequest request);

    Collection<EventShortDto> getEvents(String text,
                                        Collection<Long> categories,
                                        Boolean paid,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        boolean onlyAvailable,
                                        SortType sort,
                                        int from,
                                        int size
    );

    EventFullDto getEvent(long eventId, boolean isFirstView);
}
