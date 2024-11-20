package ru.practicum.ewmstatserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.ewmDto.Hit;
import ru.practicum.ewmDto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mapper
public interface HitMapper {
    @Mapping(source = "timestamp", target = "timestamp", qualifiedByName = "DateToString")
    HitDto entityToDto(Hit hit);

    @Mapping(source = "timestamp", target = "timestamp", qualifiedByName = "StringToDate")
    Hit dtoToEntity(HitDto dto);

    @Named("StringToDate")
    default LocalDateTime stringToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (string == null) {
            return  null;
        }
        return LocalDateTime.parse(string, formatter);
    }
    @Named("DateToString")
    default String dateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return null;
        }
        return  date.format(formatter);
    }
}
