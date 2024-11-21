package ru.practicum.ewmstatserver.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmDto.Hit;
import ru.practicum.ewmDto.HitDto;
import ru.practicum.ewmstatserver.mapper.HitMapper;
import ru.practicum.ewmstatserver.repository.StatsRepository;
import ru.practicum.ewmstatserver.service.HitService;

import java.util.Collection;

@Service
@AllArgsConstructor
public class HitServiceImpl implements HitService {
    private StatsRepository statsRepository;
    private HitMapper mapper;

    @Override
    public HitDto addHit(HitDto hitDto) {
        Hit hit = mapper.dtoToEntity(hitDto);
        boolean first = checkFirstView(hit);
        HitDto responseDto = mapper.entityToDto(statsRepository.save(hit));
        responseDto.setFirst(first);
        return responseDto;
    }

    private boolean checkFirstView(Hit hit) {
        String ip = hit.getIp();
        String uri = hit.getUri();
        Collection<Hit> hits = statsRepository.findByIpAndUri(ip, uri);
        boolean isFirst = false;
        if (hits.size() == 0) {
            isFirst = true;
        }
        return isFirst;
    }
}
