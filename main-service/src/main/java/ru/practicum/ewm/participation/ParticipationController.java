package ru.practicum.ewm.participation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/requests")
@AllArgsConstructor
public class ParticipationController {
    private final ParticipationService service;

    @GetMapping
    public Collection<ParticipationRequestDto> getUserRequests(@PathVariable long userId) {
        return service.getUserRequests(userId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addParticipationRequest(@PathVariable long userId, @RequestParam long eventId) {
        return service.addParticipationRequest(userId, eventId);
    }
    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId, @RequestParam long eventId) {
        return service.cancelRequest(userId, eventId);
    }
}
