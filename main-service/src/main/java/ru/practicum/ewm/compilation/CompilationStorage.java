package ru.practicum.ewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.model.Compilation;

@Repository
public interface CompilationStorage extends JpaRepository<Compilation, Long> {

}
