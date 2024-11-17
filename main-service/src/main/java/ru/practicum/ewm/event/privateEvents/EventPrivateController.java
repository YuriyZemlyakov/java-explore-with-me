package ru.practicum.ewm.event.privateEvents;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@AllArgsConstructor
public class EventPrivateController {
    private final EventPrivateService service;
    @GetMapping
    public Collection<EventShortDto> getEvents(@PathVariable long userId,
                                                     @RequestParam(defaultValue = "0") int from,
                                                     @RequestParam(defaultValue = "10") int size) {
        return service.getEvents(userId, from, size);
    }

    @PostMapping
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
                                  @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return service.updateEvent(userId, eventId, updateEventUserRequest);
    }
    @GetMapping("/{eventId}/requests")
    public Collection<ParticipationRequestDto> getEventParticipants(@PathVariable long userId, @PathVariable long eventId) {
        return service.getEventParticipants(userId, eventId);
    }
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestStatus(long userId, long eventId,
                                                              EventRequestStatusUpdateRequest statusUpdateRequest) {
        return service.changeRequestStatus(userId, eventId, statusUpdateRequest);
    }

}
