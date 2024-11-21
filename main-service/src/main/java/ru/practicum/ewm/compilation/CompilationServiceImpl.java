package ru.practicum.ewm.compilation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final CompilationStorage storage;
    private final EventStorage eventStorage;
    private final CompilationMapper mapper;

    @Override
    public Collection<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        return storage.findAll().stream()
                .map(compilation -> mapper.entityToDto(compilation))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return mapper.entityToDto(storage.getReferenceById(compId));
    }

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = mapper.dtoToEntity(newCompilationDto);
        if (newCompilationDto.getEvents() != null) {
            Set<Event> events = getEventsById(newCompilationDto.getEvents());
            compilation.setEvents(events);
        }
        return mapper.entityToDto(storage.save(compilation));
    }

    @Override
    public void deleteCompilation(long compId) {
        storage.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation editedCompilation = storage.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Подборка %s не найдена", compId)));
        if (updateCompilationRequest.getEvents() != null) {
            Collection<Event> updatedEventsCollection = eventStorage.findAllById(updateCompilationRequest.getEvents());
            Set<Event> updatedEventsSet = new HashSet<>(updatedEventsCollection);
            editedCompilation.setEvents(updatedEventsSet);
        }
        if (updateCompilationRequest.getTitle() != null) {
            editedCompilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.isPinned()) {
            editedCompilation.setPinned(updateCompilationRequest.isPinned());
        }
        return mapper.entityToDto(storage.save(editedCompilation));
    }

    private Set<Event> getEventsById(Set<Long> ids) {
        return eventStorage.findAllById(ids).stream()
                .collect(Collectors.toSet());
    }
}
