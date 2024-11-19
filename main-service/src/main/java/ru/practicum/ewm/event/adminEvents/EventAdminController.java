package ru.practicum.ewm.event.adminEvents;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/admin/events")
@AllArgsConstructor
public class EventAdminController {
    private final EventAdminService service;

    @GetMapping
    public Collection<EventFullDto> getEvents(@RequestParam Collection<Long> users,
                                              @RequestParam Collection<String> states,
                                              @RequestParam Collection<Long> categories,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                              @RequestParam(defaultValue = "0") long from,
                                              @RequestParam(defaultValue = "10") long size) {
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable long eventId,
                                  @Valid @RequestBody UpdateEventAdminRequest requestDto) {
        return service.editEvent(eventId, requestDto);
    }
}
