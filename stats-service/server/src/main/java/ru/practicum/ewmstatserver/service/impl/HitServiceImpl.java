package ru.practicum.ewmstatserver.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmDto.Hit;
import ru.practicum.ewmDto.HitDto;
import ru.practicum.ewmstatserver.mapper.HitMapper;
import ru.practicum.ewmstatserver.repository.StatsRepository;
import ru.practicum.ewmstatserver.service.HitService;

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
