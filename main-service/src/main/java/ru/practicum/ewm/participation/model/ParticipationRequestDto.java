package ru.practicum.ewm.participation.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    private long id;
    private long event;
    private long requester;
    private StateParticipation status;
    private LocalDateTime created;
}
