package ru.practicum.ewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.user.User;

import java.util.Collection;

public interface CompilationStorage extends JpaRepository<Compilation, Long> {
    @Query(value = "select * from compilation c join event_compilation ev on c.id = ev.compilation_id order by c.id " +
            "offset ?1 limit ?2", nativeQuery = true)
    Collection<Compilation> findWithPagination(int from, int size);
}
