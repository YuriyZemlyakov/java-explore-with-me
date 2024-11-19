package ru.practicum.ewm.event.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.event.model.*;

@Mapper
public interface EventMapper {
    @Mapping(source = "location.lat", target = "locationLat")
    @Mapping(source = "location.lon", target = "locationLon")
    @Mapping(target = "category", ignore = true)
    Event newDtoToEvent(NewEventDto dto);
    @Mapping(source = "location.lat", target = "locationLat")
    @Mapping(source = "location.lon", target = "locationLon")
    @Mapping(target = "category", ignore = true)
    Event updateAdminDtoToEvent(UpdateEventAdminRequest dto);

    @Mapping(source = "location.lat", target = "locationLat")
    @Mapping(source = "location.lon", target = "locationLon")
    @Mapping(target = "category", ignore = true)
    Event updateUserDtoToEvent(UpdateEventUserRequest dto);
    @Mapping(source = "locationLat", target = "location.lat")
    @Mapping(source = "locationLon", target = "location.lon")
    EventFullDto eventToDto(Event event);

    EventShortDto eventToShortDto(Event event);

}
