package ru.practicum.ewm.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentStorage extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndAuthor_Id(long commentId, long authorId);

    Collection<Comment> findByEvent_Id(long eventId);
}
