package ru.practicum.ewmstat.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstat.model.dto.HitDto;
import ru.practicum.ewmstat.model.dto.StatsDto;
import ru.practicum.ewmstat.service.HitService;
import ru.practicum.ewmstat.service.StatsService;
import ru.practicum.ewmstat.service.impl.HitServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@AllArgsConstructor
@Slf4j
public class StatsController {
    HitService hitService;
    StatsService statsService;
    @PostMapping("/hit")
    public HitDto addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Получен запрос: {}", hitDto);
        return hitService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public Collection<StatsDto> getStats(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                         @RequestParam @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime  end ,
                                         @RequestParam Collection<String> uris,
                                         @RequestParam boolean unique) {
        return statsService.getStats(start,end, uris, unique);

    }


}
