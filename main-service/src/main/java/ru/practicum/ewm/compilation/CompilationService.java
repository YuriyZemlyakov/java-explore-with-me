package ru.practicum.ewm.compilation;

import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {
    //    Public
    Collection<CompilationDto> getCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilation(long compId);

    //    Admin
    CompilationDto saveCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest);


}
