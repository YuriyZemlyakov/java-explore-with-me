package ru.practicum.ewmstat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmstat.model.Hit;
import ru.practicum.ewmstat.model.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("select new ru.practicum.ewmstat.model.dto.StatsDto(h.app, h.uri, count(distinct ip) as hits) from Hit h where h.timestamp between ?1 and ?2 and h.uri in (?3) group by h.app, h.uri")
    Collection<StatsDto> getStatsUnique(LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("select new ru.practicum.ewmstat.model.dto.StatsDto(h.app, h.uri, count(ip) as hits) from Hit h where h.timestamp between ?1 and ?2 and h.uri in (?3) group by h.app, h.uri")
    Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris);
}
