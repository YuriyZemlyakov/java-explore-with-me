package ru.practicum.ewmstat.service;

import ru.practicum.ewmstat.model.Hit;
import ru.practicum.ewmstat.model.dto.HitDto;

public interface HitService {
    HitDto addHit(HitDto dto);
}
