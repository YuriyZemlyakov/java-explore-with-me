package ru.practicum.ewmstatserver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmDto.HitDto;
import ru.practicum.ewmDto.StatsDto;
import ru.practicum.ewmstatserver.service.HitService;
import ru.practicum.ewmstatserver.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@AllArgsConstructor
@Slf4j
public class StatsController {
    private HitService hitService;
    private StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Получен запрос POST: {}", hitDto);
        return hitService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public Collection<StatsDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                         @RequestParam(required = false) Collection<String> uris,
                                         @RequestParam(required = false) boolean unique) {
        log.info("Получен запрос GET: {}, {}, {}, {}", start, end, uris, unique);
        return statsService.getStats(start, end, uris, unique);

    }


}
