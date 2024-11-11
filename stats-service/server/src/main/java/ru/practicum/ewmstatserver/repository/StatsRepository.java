package ru.practicum.ewmstatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmDto.Hit;
import ru.practicum.ewmDto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("select new ru.practicum.ewmDto.StatsDto(h.app, h.uri, count(distinct ip) as hits) from Hit h " +
            "where h.timestamp between ?1 and ?2 and (?3 is null or h.uri in (?3)) group by h.app, h.uri " +
            "order by hits desc")
    Collection<StatsDto> getStatsUnique(LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("select new ru.practicum.ewmDto.StatsDto(h.app, h.uri, count(ip) as hits) from Hit h " +
            "where h.timestamp between ?1 and ?2 and (?3 is null or h.uri  in (?3)) group by h.app, h.uri " +
            "order by hits desc")
    Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris);
}
