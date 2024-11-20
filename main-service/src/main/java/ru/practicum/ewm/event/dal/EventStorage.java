package ru.practicum.ewm.event.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;

import java.util.Collection;
import java.util.Optional;

public interface EventStorage extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    @Query(value = "select e.* from event e where e.user_id = ?1 order by e.id offset ?2 limit ?3", nativeQuery = true)
    Collection<Event> findWithPagination(long userId, int from, int size);

    Optional<Event> findByIdAndState(long eventId, StateEvent state);
}
