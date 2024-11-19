package ru.practicum.ewm.participation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.privateEvents.EventPrivateServiceImpl;
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
        Participation participation = new Participation();
        participation.setRequester(userId);
        participation.setEvent(eventId);
        participation.setStatus(StateParticipation.PENDING);
        participation.setCreated(LocalDateTime.now());
        return mapper.entityToDto(storage.save(participation));
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long eventId) {
        Participation participation = storage.findByEventAndRequester(eventId, userId)
                .orElseThrow(()-> new NotFoundException(String.format("запрос на участие пользователя %s в событии" +
                        " %s не найден", userId, eventId)));
        if (participation.getStatus() == StateParticipation.CONFIRMED
                || participation.getStatus() == StateParticipation.PENDING ) {
            if (participation.getStatus() == StateParticipation.CONFIRMED) {
                Event event = eventStorage.findById(eventId).orElseThrow(() -> new NotFoundException(String
                        .format("Event %s не найден", eventId)));
                event.setConfirmedRequests(event.getConfirmedRequests() - 1);
                eventStorage.save(event);
            }
            participation.setStatus(StateParticipation.REJECTED);
        } else {
            throw new ValidationException("Заявка уже была отклонена ранее");
        }
        return mapper.entityToDto(storage.save(participation));
    }
}
