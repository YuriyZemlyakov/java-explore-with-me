package ru.practicum.ewm.participation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.participation.model.Participation;
import ru.practicum.ewm.participation.model.StateParticipation;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ParticipationStorage extends JpaRepository<Participation, Long> {
    Optional<Participation> findByEventAndRequester(long event, long requester);

    Collection<Participation> findByStatus(StateParticipation status);

    Collection<Participation> findByRequester(long requester);

    Collection<Participation> findAllByEventAndRequester(long eventId, long userId);

    Collection<Participation> findByEvent(long event);


}
