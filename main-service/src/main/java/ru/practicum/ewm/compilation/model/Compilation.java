package ru.practicum.ewm.compilation.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.ewm.event.model.Event;

import java.util.Set;

@Entity
@Data
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany
    @JoinTable(name = "event_compilation", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
    private String title;
    private boolean pinned;
}
