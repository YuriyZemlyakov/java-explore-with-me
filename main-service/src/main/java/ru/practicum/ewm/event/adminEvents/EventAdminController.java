package ru.practicum.ewm.event.adminEvents;

import lombok.AllArgsConstructor;
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
                                              @RequestParam Collection<String> stats,
                                              @RequestParam Collection<Long> categories,
                                              @RequestParam LocalDateTime rangeStart,
                                              @RequestParam LocalDateTime rangeEnd,
                                              @RequestParam long from,
                                              @RequestParam long size) {
        return service.getEvents(users, stats, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable long eventId, UpdateEventAdminRequest requestDto) {
        return service.editEvent(eventId, requestDto);
    }
}
