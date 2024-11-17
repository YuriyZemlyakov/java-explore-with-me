package ru.practicum.ewm.event.adminEvents;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.UpdateEventAdminRequest;
import ru.practicum.ewm.event.model.mapper.EventMapper;

import java.time.LocalDateTime;
import java.util.Collection;

//ToDo добавить методы для сложной фильтрации
@Service
@AllArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    private final EventAdminStorage storage;
    private final EventMapper mapper;
    @Override
    public Collection<EventFullDto> getEvents(Collection<Long> users, Collection<String> stats, Collection<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, long from, long size) {
        return null;
    }

    @Override
    public EventFullDto editEvent(long eventId, UpdateEventAdminRequest updateDto) {
        Event event = mapper.updateAdminDtoToEvent(updateDto);
        return mapper.eventToDto(storage.save(event));
    }
}
