package ru.practicum.ewmstatserver.service;

import ru.practicum.ewmDto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsService {
    Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                  Collection<String> uris, boolean unique);
}
