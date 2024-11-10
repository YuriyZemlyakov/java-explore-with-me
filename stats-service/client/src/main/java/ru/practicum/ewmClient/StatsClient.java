package ru.practicum.ewmClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmDto.HitDto;

public class StatsClient {
    //    private static final String STATS_SERVER_URL = "${stats-server.url}";
    private static final String STATS_SERVER_URL = "http://localhost:9090";
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<HitDto> sendHit(HitDto hitDto) {
        HttpEntity<HitDto> request = new HttpEntity<>(hitDto);
        ResponseEntity<HitDto> response = restTemplate.exchange(
                STATS_SERVER_URL + "/hit",
                HttpMethod.POST,
                request,
                HitDto.class
        );
        return response;

    }
}
