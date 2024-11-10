package ru.practicum.ewmstat.model.dto;

import lombok.Data;

@Data
public class StatsDto {
    private String app;
    private String uri;
    private long hits;

    public StatsDto(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
