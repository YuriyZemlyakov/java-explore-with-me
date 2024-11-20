package ru.practicum.ewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

public interface CompilationStorage extends JpaRepository<Compilation, Long> {

}
