package ru.practicum.ewm.event.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.User;

import java.util.Collection;

public interface EventStorage extends JpaRepository<Event, Long> {
    Event findByIdAndInitiatorId(long eventId, long initiatorId);

    @Query(value = "select e.* from event e where e.user_id = ?1 order by e.id offset ?2 limit ?3", nativeQuery = true)
    Collection<Event> findWithPagination(long userId, int from, int size);
}
