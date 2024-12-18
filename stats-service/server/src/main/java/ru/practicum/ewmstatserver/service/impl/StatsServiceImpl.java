package ru.practicum.ewmstatserver.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmDto.StatsDto;
import ru.practicum.ewmstatserver.exception.ValidationException;
import ru.practicum.ewmstatserver.repository.StatsRepository;
import ru.practicum.ewmstatserver.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    private StatsRepository statsRepository;

    @Override
    public Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                         Collection<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new ValidationException("Начальная дата не должна быть позже конечной");
        }
        if (unique) {
            return statsRepository.getStatsUnique(start, end, uris);
        } else {
            return statsRepository.getStats(start, end, uris);
        }

    }
}
