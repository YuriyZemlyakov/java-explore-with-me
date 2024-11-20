package ru.practicum.ewm.event.publicEvents;

import com.querydsl.core.BooleanBuilder;
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
    public Collection<EventShortDto> getEvents(String text, Collection<Long> categories, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                                               SortType sort, int from, int size) {


        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException("Начальная дата не может быть позже конечной");
        }
        BooleanBuilder builder = new BooleanBuilder();
        if (text != null && !text.isBlank()) {
            builder.and(QEvent.event.annotation.likeIgnoreCase(text))
                    .or(QEvent.event.description.likeIgnoreCase(text));
        }
        if (categories != null) {
            builder.and(QEvent.event.category.id.in(categories));
        }
        if (paid != null) {
            builder.and(QEvent.event.paid.eq(paid));
        }
        if (rangeStart != null) {
            builder.and(QEvent.event.eventDate.gt(rangeStart));
        }
        if (rangeEnd != null) {
            builder.and(QEvent.event.eventDate.lt(rangeEnd));
        }
        builder.and(QEvent.event.state.eq(StateEvent.PUBLISHED));

        Iterable<Event> eventIterable=  storage.findAll(builder);
        Collection<Event> events = new ArrayList<>();
        eventIterable.forEach(event -> {
            events.add(event);
        });

        return events.stream()
                .map(event -> mapper.eventToShortDto(event))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(long eventId, boolean isFirstView) {
        Event event = storage.findByIdAndState(eventId, StateEvent.PUBLISHED)
                .orElseThrow(()-> new NotFoundException(String
                .format("Event %s не найден", eventId)));
        if (isFirstView) {
            event.setViews(event.getViews() + 1);
            storage.save(event);
        }
        return mapper.eventToDto(event);
    }
}
