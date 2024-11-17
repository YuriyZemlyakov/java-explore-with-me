package ru.practicum.ewm.event.adminEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Event;

public interface EventAdminStorage extends JpaRepository<Event, Long> {
}
