package ru.practicum.ewm.event.adminEvents;

import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventAdminService {
    Collection<EventFullDto> getEvents(Collection<Long> users,
                                       Collection<String> states,
                                       Collection<Long> categories,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       long from,
                                       long size);

    EventFullDto editEvent(long eventId, UpdateEventAdminRequest request);
}
