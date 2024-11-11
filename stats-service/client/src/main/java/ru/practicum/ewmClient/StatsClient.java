package ru.practicum.ewmClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmDto.HitDto;

public class StatsClient {
    private final String statsServerUrl;
    private final RestTemplate restTemplate;

    public StatsClient(String statsServerUrl, RestTemplate restTemplate) {
        this.statsServerUrl = statsServerUrl;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<HitDto> sendHit(HitDto hitDto) {
        HttpEntity<HitDto> request = new HttpEntity<>(hitDto);
        ResponseEntity<HitDto> response = restTemplate.exchange(
                statsServerUrl + "/hit",
                HttpMethod.POST,
                request,
                HitDto.class
        );
        return response;

    }
}
