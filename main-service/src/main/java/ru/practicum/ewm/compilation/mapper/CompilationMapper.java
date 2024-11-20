package ru.practicum.ewm.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", ignore = true)
    Compilation dtoToEntity(NewCompilationDto dto);

    @Mapping(target = "events", ignore = true)
    Compilation updateDtoToEntity(UpdateCompilationRequest dto);

    CompilationDto entityToDto(Compilation compilation);
}
