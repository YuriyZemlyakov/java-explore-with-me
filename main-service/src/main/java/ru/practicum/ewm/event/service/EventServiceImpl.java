package ru.practicum.ewm.event.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.CategoryStorage;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.model.mapper.EventMapper;
import ru.practicum.ewm.event.publicEvents.SortType;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.participation.ParticipationStorage;
import ru.practicum.ewm.participation.model.Participation;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;
import ru.practicum.ewm.participation.model.StateParticipation;
import ru.practicum.ewm.participation.model.mapper.ParticipationMapper;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserStorage;
import ru.practicum.ewm.validator.EventValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventMapper mapper;
    private final ParticipationMapper participationMapper;
    private final EventStorage storage;
    private final CategoryStorage categoryStorage;
    private final UserStorage userStorage;
    private final ParticipationStorage participationStorage;

    @Override
    public Collection<EventShortDto> getEvents(long userId, int from, int size) {
        return storage.findWithPagination(userId, from, size).stream()
                .map(event -> mapper.eventToShortDto(event))
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto addEvent(long userId, NewEventDto newEventDto) {
        EventValidator.validateEventDateNew(newEventDto);
        Event event = mapper.newDtoToEvent(newEventDto);
        long categoryId = newEventDto.getCategory();
        event.setCategory(categoryStorage.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Категория %d не найдена", categoryId))));
        User user = userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User не найден"));
        event.setInitiator(user);
        event.setState(StateEvent.PENDING);
        return mapper.eventToDto(storage.save(event));
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        Event event = storage.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event %s не найден", eventId)));
        if (event.getInitiator().getId() != userId) {
            throw new ConflictException(String.format("Пользователь %s не являтеся инициатором события и не может " +
                    "просматривать его", userId));
        }
        return mapper.eventToDto(event);
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event oldEvent = storage.findById(eventId).orElseThrow(() -> new NotFoundException("eventId не найден"));
        if (oldEvent.getState() == StateEvent.PUBLISHED) {
            throw new ConflictException("Событие в статусе PUBLISHED не подлежит редактированию");
        }
        EventValidator.validateEventDateUser(updateEventUserRequest);
        Event editedEvent = buildEditedObject(oldEvent, updateEventUserRequest);

        return mapper.eventToDto(storage.save(editedEvent));
    }

    @Override
    public Collection<ParticipationRequestDto> getEventParticipants(long userId, long eventId) {
        Event event = storage.findByIdAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new NotFoundException(String
                        .format("Event %s не найден", eventId)));
        Collection<Participation> participations = participationStorage.findByEvent(eventId);
        return participations.stream()
                .map(participation -> participationMapper.entityToDto(participation))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult changeRequestStatus(long userId, long eventId,
                                                              EventRequestStatusUpdateRequest request) {
        log.info("Старт обработки в методе изменения статуса");
        StateParticipation status = request.getStatus();
        Event event = storage.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event %s не найден", eventId)));
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConflictException("Модерация не требуется");
        }
        if (userId != event.getInitiator().getId()) {
            throw new ConflictException(String.format("Пользователь %s не имеет права запрашиать информацию " +
                    "об участниках события %s", userId, eventId));
        }

        log.info("Предварительные проверки пройдены успешно");
        Collection<Participation> participations = participationStorage.findAllById(request.getRequestIds());
        checkStatus(participations);

        if (status == StateParticipation.CONFIRMED) {
            log.info("Требуется установить статус CONFIRMED");
            checkParticipantsLimit(event, request);
            event.setConfirmedRequests(event.getConfirmedRequests() + request.getRequestIds().size());
            log.info("Корректировка поля confirmedRequests");
            participations.stream()
                    .forEach(participation -> participation.setStatus(status));
            log.info("Обновление поля статус в заявках на участие");
            storage.save(event);
            log.info("Сохранение события с обновленным числом подтвержденных участников");
        }
        if (status == StateParticipation.REJECTED) {
            if (request.getRequestIds().size() < event.getConfirmedRequests()) {
                event.setConfirmedRequests(event.getConfirmedRequests() - request.getRequestIds().size());
            } else {
                event.setConfirmedRequests(0);
            }
            participations.stream()
                    .forEach(participation -> participation.setStatus(status));
        }
        Collection<ParticipationRequestDto> updatedParticipations = participationStorage.saveAll(participations).stream()
                .map(participation -> participationMapper.entityToDto(participation))
                .collect(Collectors.toList());
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (StateParticipation.CONFIRMED == status) {
            result.setConfirmedRequests(updatedParticipations);
        } else {
            result.setRejectedRequests(updatedParticipations);
        }
        return result;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
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
        Event updatedEvent = buildEditedObject(oldEvent, updateDto);
        return mapper.eventToDto(storage.save(updatedEvent));
    }

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

        Iterable<Event> eventIterable = storage.findAll(builder);
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
                .orElseThrow(() -> new NotFoundException(String
                        .format("Event %s не найден", eventId)));
        if (isFirstView) {
            event.setViews(event.getViews() + 1);
            storage.save(event);
        }
        return mapper.eventToDto(event);
    }

    private void checkParticipantsLimit(Event event, EventRequestStatusUpdateRequest request) {
        long confirmedRequests = event.getConfirmedRequests();
        long participantLimit = event.getParticipantLimit();
        if (request.getRequestIds().size() > (participantLimit - confirmedRequests)) {
            throw new ConflictException("Превышено максимальное число участников");
        }
        log.info("Проверка на максимальное число участников пройдена успешно");
    }

    private void checkStatus(Collection<Participation> participations) {
        for (Participation participation : participations) {
            if (participation.getStatus() != StateParticipation.PENDING) {
                throw new ConflictException(String.format("Заявка должна находиться в статусе %s",
                        StateParticipation.PENDING));
            }
        }
        log.info("Проверка на свободные места пройдена успешно");
    }

    private Event buildEditedObject(Event event, UpdateEventUserRequest updatedFields) {
        Optional.ofNullable(updatedFields.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updatedFields.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updatedFields.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(updatedFields.getLocation()).ifPresent(location -> {
            event.setLocationLat(location.getLat());
            event.setLocationLon(location.getLon());
        });
        Optional.ofNullable(updatedFields.getStateAction()).ifPresent(stateActionUser -> {
            if (stateActionUser == StateActionUser.SEND_TO_REVIEW) {
                event.setState(StateEvent.PENDING);
            }
            if (stateActionUser == StateActionUser.CANCEL_REVIEW) {
                event.setState(StateEvent.CANCELED);
            }

        });
        Optional.ofNullable(updatedFields.getTitle()).ifPresent(event::setTitle);
        if (updatedFields.getCategory() != 0) {
            event.setCategory(getCategoryById(updatedFields.getCategory()));
        }
        if (updatedFields.getParticipantLimit() > 0) {
            event.setParticipantLimit(updatedFields.getParticipantLimit());
        }
        return event;
    }

    private Category getCategoryById(long categoryId) {
        return categoryStorage.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Категория %s не найдена", categoryId)));
    }

    private Event buildEditedObject(Event event, UpdateEventAdminRequest updatedFields) {
        Optional.ofNullable(updatedFields.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updatedFields.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updatedFields.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(updatedFields.getLocation()).ifPresent(location -> {
            event.setLocationLat(location.getLat());
            event.setLocationLon(location.getLon());
        });
        Optional.ofNullable(updatedFields.getStateAction()).ifPresent(stateActionAdmin -> {
            if (stateActionAdmin == StateActionAdmin.REJECT_EVENT) {
                event.setState(StateEvent.CANCELED);
            }
            if (stateActionAdmin == StateActionAdmin.PUBLISH_EVENT) {
                event.setState(StateEvent.PUBLISHED);
            }
        });
        Optional.ofNullable(updatedFields.getTitle()).ifPresent(event::setTitle);
        if (updatedFields.getCategory() != 0) {
            event.setCategory(getCategoryById(updatedFields.getCategory()));
        }
        if (updatedFields.getParticipantLimit() > 0) {
            event.setParticipantLimit(updatedFields.getParticipantLimit());
        }
        event.setPaid(updatedFields.isPaid());
        return event;
    }

    private Collection<StateEvent> convertStringToEnum(Collection<String> states) {
        Collection<StateEvent> statesList = states.stream()
                .map(state -> StateEvent.valueOf(state))
                .collect(Collectors.toList());
        return statesList;
    }
}
