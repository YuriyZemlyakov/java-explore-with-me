package ru.practicum.ewm.event.publicEvents;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.EventShortDto;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventPublicController {
    private final EventPublicService service;
    @GetMapping
    public Collection<EventShortDto> getEvents(String text,
                                        @RequestParam(required = false) Collection<Long> categories,
                                        @RequestParam(required = false) boolean paid,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" ) LocalDateTime rangeStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )LocalDateTime rangeEnd,
                                        @RequestParam(required = false) boolean onlyAvailable,
                                        @RequestParam(required = false) SortType sort,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getEvents(text,categories,paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable long eventId) {
        return service.getEvent(eventId);
    }

}
