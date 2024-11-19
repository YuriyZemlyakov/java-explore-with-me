package ru.practicum.ewm.event.publicEvents;

import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.EventShortDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventPublicService {
    Collection<EventShortDto> getEvents(String text,
                                        Collection<Long> categories,
                                        boolean paid,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        boolean onlyAvailable,
                                        SortType sort,
                                        int from,
                                        int size
                                        );

    EventFullDto getEvent(long eventId);
}
