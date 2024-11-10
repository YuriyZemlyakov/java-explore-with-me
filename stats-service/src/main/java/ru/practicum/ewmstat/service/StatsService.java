package ru.practicum.ewmstat.service;

import ru.practicum.ewmstat.model.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsService {
    Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                  Collection<String> uris, boolean unique);
}
