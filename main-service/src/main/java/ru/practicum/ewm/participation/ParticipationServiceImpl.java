package ru.practicum.ewm.participation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.privateEvents.EventPrivateServiceImpl;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.participation.model.Participation;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;
import ru.practicum.ewm.participation.model.StateParticipation;
import ru.practicum.ewm.participation.model.mapper.ParticipationMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {
    private final ParticipationMapper mapper;
    private final ParticipationStorage storage;
    private final EventStorage eventStorage;
    private final EventPrivateServiceImpl eventPrivateService;

    @Override
    public Collection<ParticipationRequestDto> getUserRequests(long userId) {
        return storage.findByRequester(userId).stream()
                .map(participation -> mapper.entityToDto(participation))
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(long userId, long eventId) {
        Event event = getEvent(eventId);
        if (userId == event.getInitiator().getId()) {
            throw new ConflictException("Инициатор события не может регистрироваться в своем событии");
        }
        if (StateEvent.PUBLISHED != event.getState()) {
            throw new ConflictException("Можно зарегистрироваться только на опубликованное событие");
        }
        checkParticipantsLimit(event);
        Collection<Participation> existingParticipations = storage.findAllByEventAndRequester(eventId, userId);
        if (!existingParticipations.isEmpty()) {
            throw new ConflictException(String
                    .format("Пользователь %s уже зарегистрирован на событие %s", userId, eventId));
        }

        Participation participation = new Participation();
        participation.setRequester(userId);
        participation.setEvent(eventId);
        if (event.getParticipantLimit() == 0) {
            participation.setStatus(StateParticipation.CONFIRMED);
        } else if (!event.isRequestModeration()) {
            if (event.getParticipantLimit() - event.getConfirmedRequests() > 0) {
                participation.setStatus(StateParticipation.CONFIRMED);
                updateConfirmedRequests(event, StateParticipation.CONFIRMED);
            } else {
                participation.setStatus(StateParticipation.REJECTED);
                updateConfirmedRequests(event, StateParticipation.REJECTED);
            }
        } else {
            participation.setStatus(StateParticipation.PENDING);
        }
        participation.setCreated(LocalDateTime.now());
        return mapper.entityToDto(storage.save(participation));
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        Participation participation = storage.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("запрос на участие %S не найден", requestId)));
        if (participation.getStatus() == StateParticipation.CONFIRMED
                || participation.getStatus() == StateParticipation.PENDING) {
            if (participation.getStatus() == StateParticipation.CONFIRMED) {
                Event event = getEvent(participation.getEvent());
                updateConfirmedRequests(event, StateParticipation.CANCELED);
            }
            participation.setStatus(StateParticipation.CANCELED);
        } else {
            throw new ValidationException("Заявка уже была отклонена ранее");
        }
        return mapper.entityToDto(storage.save(participation));
    }

    private Event getEvent(long eventId) {
        return eventStorage.findById(eventId).orElseThrow(() -> new NotFoundException(String
                .format("Event %s не найден", eventId)));
    }

    private void updateConfirmedRequests(Event event, StateParticipation state) {
        long freeLimit = event.getParticipantLimit() - event.getConfirmedRequests();

        if (StateParticipation.CONFIRMED == state) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventStorage.save(event);
        }
        if (StateParticipation.REJECTED == state) {
            if (freeLimit > 0) {
                event.setConfirmedRequests(event.getConfirmedRequests() - 1);
                eventStorage.save(event);
            }
        }
    }

    private void checkParticipantsLimit(Event event) {
        if (event.getParticipantLimit() != 0 && (event.getParticipantLimit() - event.getConfirmedRequests()) == 0) {
            throw new ConflictException("Лимит участников исчерпан");
        }
    }
}
