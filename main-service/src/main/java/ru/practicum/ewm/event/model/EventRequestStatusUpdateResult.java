package ru.practicum.ewm.event.model;

import lombok.Data;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

@Data
public class EventRequestStatusUpdateResult {
    private final Collection<ParticipationRequestDto> confirmedRequests;
    private final Collection<ParticipationRequestDto> rejectedRequests;
}
