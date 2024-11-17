package ru.practicum.ewm.event.model;

import lombok.Data;
import ru.practicum.ewm.participation.model.StateParticipation;

import java.util.Collection;

@Data
public class EventRequestStatusUpdateRequest {
    private Collection<Long> ids;
    private StateParticipation status;

}
