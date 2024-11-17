package ru.practicum.ewm.event.privateEvents;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.CategoryStorage;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.model.mapper.EventMapper;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.participation.ParticipationStorage;
import ru.practicum.ewm.participation.model.Participation;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;
import ru.practicum.ewm.participation.model.StateParticipation;
import ru.practicum.ewm.participation.model.mapper.ParticipationMapper;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {
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
        Event event = mapper.newDtoToEvent(newEventDto);
        long categoryId = newEventDto.getCategory();
        event.setCategory(categoryStorage.findById(categoryId)
                .orElseThrow(()-> new NotFoundException(String.format("Категория %d не найдена", categoryId))));
        User user = userStorage.findById(userId).orElseThrow(() -> new NotFoundException("User не найден"));
        event.setInitiator(user);
        event.setState(StateEvent.PENDING);
        return mapper.eventToDto(storage.save(event));
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        return mapper.eventToDto(storage.findByIdAndInitiatorId(eventId, userId));
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = mapper.updateUserDtoToEvent(updateEventUserRequest);
        return mapper.eventToDto(storage.save(event));
    }
    @Override
    public Collection<ParticipationRequestDto> getEventParticipants(long userId, long eventId) {
        return participationStorage.findByEventAndRequester(eventId, userId).stream()
                .map(participation -> participationMapper.entityToDto(participation))
                .collect(Collectors.toList());
    }
    @Override
    public EventRequestStatusUpdateResult changeRequestStatus(long userId, long eventId,
                                                              EventRequestStatusUpdateRequest request) {
        StateParticipation status = request.getStatus();
        Collection<Participation> participations = participationStorage.findAllById(request.getIds());
        participations.stream()
                .forEach(participation -> participation.setStatus(status));
        Collection<Participation> savedParticipations = participationStorage.saveAll(participations);
        Collection<ParticipationRequestDto> confirmedRequests = participationStorage.findByStatus(StateParticipation.CONFIRMED)
                .stream()
                .map(participation -> participationMapper.entityToDto(participation))
                .collect(Collectors.toList());
        Collection<ParticipationRequestDto> rejectedRequests = participationStorage.findByStatus(StateParticipation.REJECTED)
                .stream()
                .map(participation -> participationMapper.entityToDto(participation))
                .collect(Collectors.toList());

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
        return result;

    }
}
