package ru.practicum.ewmstat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmstat.model.dto.StatsDto;
import ru.practicum.ewmstat.repository.StatsRepository;
import ru.practicum.ewmstat.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    StatsRepository statsRepository;
    @Override
    public Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                         Collection<String> uris, boolean unique) {
        if (unique) {
            return statsRepository.getStatsUnique(start, end, uris);
        } else {
            return  statsRepository.getStats(start, end, uris);
        }

    }
}
