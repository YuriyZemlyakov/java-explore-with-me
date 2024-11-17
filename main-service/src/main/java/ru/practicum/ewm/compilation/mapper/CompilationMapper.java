package ru.practicum.ewm.compilation.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;
import ru.practicum.ewm.event.adminEvents.EventAdminService;
import ru.practicum.ewm.event.adminEvents.EventAdminServiceImpl;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventShortDto;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", ignore = true)
    Compilation dtoToEntity(NewCompilationDto dto);
    Compilation updateDtoToEntity(UpdateCompilationRequest dto);
    CompilationDto entityToDto(Compilation compilation);
}
