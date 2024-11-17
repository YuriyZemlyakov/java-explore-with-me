package ru.practicum.ewm.compilation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;
import ru.practicum.ewm.event.dal.EventStorage;
import ru.practicum.ewm.event.model.Event;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationStorage storage;
    private final EventStorage eventStorage;
    private final CompilationMapper mapper;
    @Override
    public Collection<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        return storage.findWithPagination(from, size).stream()
                .map(compilation -> mapper.entityToDto(compilation))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return mapper.entityToDto(storage.getReferenceById(compId));
    }

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = mapper.dtoToEntity(newCompilationDto);
        Set<Event> events = getEventsById(newCompilationDto.getEvents());
        compilation.setEvents(events);
        return mapper.entityToDto(storage.save(compilation));
    }

    @Override
    public void deleteCompilation(long compId) {
        storage.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation editedCompilation = mapper.updateDtoToEntity(updateCompilationRequest);
        return mapper.entityToDto(storage.save(editedCompilation));
    }
    private Set<Event> getEventsById(Set<Long> ids) {
        return eventStorage.findAllById(ids).stream()
                .collect(Collectors.toSet());
    }
}
