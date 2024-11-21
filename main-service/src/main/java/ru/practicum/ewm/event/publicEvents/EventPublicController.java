package ru.practicum.ewm.event.publicEvents;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewmClient.StatsClient;
import ru.practicum.ewmDto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventPublicController {
    private final EventService service;
    private final StatsClient client;
    private static final String APP = "main-service";

    @GetMapping
    public Collection<EventShortDto> getEvents(String text,
                                               @RequestParam(required = false) Collection<Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(required = false) boolean onlyAvailable,
                                               @RequestParam(required = false) SortType sort,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size,
                                               HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HitDto hitDto = HitDto.builder()
                .app(APP)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
        client.sendHit(hitDto);
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable long eventId,
                                 HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HitDto hitDto = HitDto.builder()
                .app(APP)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        HitDto responseDto = client.sendHit(hitDto).getBody();
        boolean isFirstView = responseDto.getFirst();
        return service.getEvent(eventId, isFirstView);
    }

}
