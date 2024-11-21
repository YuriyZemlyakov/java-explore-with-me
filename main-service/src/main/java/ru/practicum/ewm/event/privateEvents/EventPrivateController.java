package ru.practicum.ewm.event.privateEvents;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@AllArgsConstructor
@Slf4j
public class EventPrivateController {
    private final EventService service;

    @GetMapping
    public Collection<EventShortDto> getEvents(@PathVariable long userId,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        return service.getEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable long userId,
                                 @Valid @RequestBody NewEventDto newEventDto) {
        return service.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable long userId, @PathVariable long eventId) {
        return service.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return service.updateEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public Collection<ParticipationRequestDto> getEventParticipants(@PathVariable long userId, @PathVariable long eventId) {
        return service.getEventParticipants(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestStatus(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @Valid @RequestBody EventRequestStatusUpdateRequest statusUpdateRequest) {
        log.info("Получен PATCH-запрос с параметрами userId = {}, eventId = {} и телом сообщения: {}", userId,
                eventId, statusUpdateRequest);
        return service.changeRequestStatus(userId, eventId, statusUpdateRequest);
    }

}
