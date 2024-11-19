package ru.practicum.ewm.event.publicEvents;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.model.mapper.EventMapper;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Optional.*;

@Service
@AllArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {
    private final EventStorage storage;
    private final EventMapper mapper;

    @Override
    public Collection<EventShortDto> getEvents(String text, Collection<Long> categories, boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                                               SortType sort, int from, int size) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException("Начальная дата не может быть позже конечной");
        }
        BooleanExpression byText = text != null ? QEvent.event.annotation.likeIgnoreCase("%" + text + "%") : null;
        BooleanExpression byCategories = categories != null ? QEvent.event.category.id.in(categories): null;
        BooleanExpression byPaid =  QEvent.event.paid.eq(paid);
        BooleanExpression byStart = rangeStart != null ? QEvent.event.eventDate.gt(rangeStart): null;
        BooleanExpression byEnd = rangeEnd != null ? QEvent.event.eventDate.lt(rangeEnd): null;
        BooleanExpression byState = QEvent.event.state.eq(StateEvent.PUBLISHED);

        Iterable<Event> eventIterable=  storage.findAll(byText.and(byCategories).and(byPaid).and(byStart).and(byEnd)
                .and(byState));
        Collection<Event> events = new ArrayList<>();
        eventIterable.forEach(event -> events.add(event));

        return events.stream()
                .map(event -> mapper.eventToShortDto(event))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(long eventId) {
        Event event = storage.findByIdAndState(eventId, StateEvent.PUBLISHED)
                .orElseThrow(()-> new NotFoundException(String
                .format("Event %s не найден", eventId)));
        return mapper.eventToDto(event);
    }
}
