package ru.practicum.ewm.validator;

import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;

public class EventValidator {
    public static void validateEventDateAdmin(UpdateEventAdminRequest updateDto) {
        if (updateDto.getEventDate() != null) {
            if (updateDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                throw new ValidationException("дата начала изменяемого события должна быть не ранее чем за час от " +
                        "даты публикации");
            }
        }
    }

    public static void validateEventDateUser(UpdateEventUserRequest updateDto) {
        if (updateDto.getEventDate() != null) {
            if (updateDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ValidationException("дата начала изменяемого события должна быть не ранее чем за 2 часа от " +
                        "даты публикации");
            }
        }
    }

    public static void validateEventDateNew(NewEventDto newEventDto) {
        if (newEventDto.getEventDate() != null) {
            if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ValidationException("дата начала изменяемого события должна быть не ранее чем за 2 часа от " +
                        "даты публикации");
            }
        }
    }


    public static Event checkEventExists(EventStorage storage, long eventId) {
        return storage.findById(eventId).orElseThrow(() -> new NotFoundException(String
                .format("Event %s не найден", eventId)));
    }

    public static void checkRejectAction(Event event) {
        if (event.getState() != StateEvent.PENDING) {
            throw new ConflictException("Событие можно отклонить только если оно в статусе PENDING");
        }
    }
}
