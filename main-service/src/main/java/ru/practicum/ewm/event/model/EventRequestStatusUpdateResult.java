package ru.practicum.ewm.event.model;

import lombok.Data;
import ru.practicum.ewm.participation.model.ParticipationRequestDto;

import java.util.Collection;

@Data
public class EventRequestStatusUpdateResult {
    private Collection<ParticipationRequestDto> confirmedRequests;
    private Collection<ParticipationRequestDto> rejectedRequests;

    public EventRequestStatusUpdateResult() {
    }
}
