package ru.practicum.ewm.event.adminEvents;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.CategoryStorage;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.model.mapper.EventMapper;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.validator.EventValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//ToDo добавить методы для сложной фильтрации
@Service
@AllArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    private final EventStorage storage;
    private final EventMapper mapper;
    private final CategoryStorage categoryStorage;
    @Override
    @Transactional(readOnly = true)
    public Collection<EventFullDto> getEvents(Collection<Long> users,
                                              Collection<String> states,
                                              Collection<Long> categories,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              long from,
                                              long size) {
        BooleanBuilder builder = new BooleanBuilder();
        if (users != null) {
            builder.and(QEvent.event.initiator.id.in(users));
        }
        if (states != null) {
            builder.and(QEvent.event.state.in(convertStringToEnum(states)));
        }
        if (categories != null) {
            builder.and(QEvent.event.category.id.in(categories));
        }
        if (rangeStart != null) {
            builder.and(QEvent.event.eventDate.gt(rangeStart));
        }
        if (rangeEnd != null) {
            builder.and(QEvent.event.eventDate.lt(rangeEnd));
        }

        Collection<Event> events = new ArrayList<>();
        Iterable<Event> eventIterable = storage.findAll(builder);
        eventIterable.forEach(event -> events.add(event));
        return events.stream()
                .map(event -> mapper.eventToDto(event))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());


    }

    @Override
    public EventFullDto editEvent(long eventId, UpdateEventAdminRequest updateDto) {
        Event oldEvent = EventValidator.checkEventExists(storage, eventId);
        EventValidator.validateEventDateAdmin(updateDto);
        EventValidator.checkRejectAction(oldEvent);
        Event updatedEvent = buildEditedObject(oldEvent,updateDto);
        return mapper.eventToDto(storage.save(updatedEvent));
    }
    private Event buildEditedObject(Event event, UpdateEventAdminRequest updatedFields) {
        Optional.ofNullable(updatedFields.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updatedFields.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updatedFields.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(updatedFields.getLocation()).ifPresent(location -> {
                event.setLocationLat(location.getLat());
                event.setLocationLon(location.getLon());
        });
//        Optional.ofNullable(updatedFields.isRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(updatedFields.getStateAction()).ifPresent(stateActionAdmin -> {
            if (stateActionAdmin == StateActionAdmin.REJECT_EVENT) {
                event.setState(StateEvent.CANCELLED);
            }
            if (stateActionAdmin == StateActionAdmin.PUBLISH_EVENT) {
                event.setState(StateEvent.PUBLISHED);
            }
        });
        Optional.ofNullable(updatedFields.getTitle()).ifPresent(event::setTitle);
        if (updatedFields.getCategory() != 0) {
            event.setCategory(getCategoryById(updatedFields.getCategory()));
        }
        if (updatedFields.getParticipantLimit() >0) {
            event.setParticipantLimit(updatedFields.getParticipantLimit());
        }
        return event;
    }
    private Category getCategoryById(long categoryId) {
        return categoryStorage.findById(categoryId)
                .orElseThrow(()-> new NotFoundException(String.format("Категория %s не найдена", categoryId)));
    }
    private Collection<StateEvent> convertStringToEnum(Collection<String> states) {
        Collection<StateEvent> statesList = states.stream()
                .map(state -> StateEvent.valueOf(state))
                .collect(Collectors.toList());
        return statesList;
    }
}
