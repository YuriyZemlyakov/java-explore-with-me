package ru.practicum.ewmstat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmstat.mapper.HitMapper;
import ru.practicum.ewmstat.model.Hit;
import ru.practicum.ewmstat.model.dto.HitDto;
import ru.practicum.ewmstat.model.dto.StatsDto;
import ru.practicum.ewmstat.repository.StatsRepository;
import ru.practicum.ewmstat.service.HitService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@AllArgsConstructor
public class HitServiceImpl implements HitService {
    StatsRepository statsRepository;
    HitMapper mapper;
    @Override
    public HitDto addHit(HitDto hitDto) {
        Hit hit = mapper.dtoToEntity(hitDto);
        return mapper.entityToDto(statsRepository.save(hit));
    }
}
