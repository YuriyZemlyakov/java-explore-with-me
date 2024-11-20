package ru.practicum.ewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.user.User;

import java.util.Collection;

public interface CompilationStorage extends JpaRepository<Compilation, Long> {

}
