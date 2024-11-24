package ru.practicum.ewm.comment;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.User;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    @Enumerated(EnumType.STRING)
    private ModificationType lastModifiedBy;
}
