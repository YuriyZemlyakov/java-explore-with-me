package ru.practicum.ewmClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewmDto.HitDto;
import ru.practicum.ewmDto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Component
public class StatsClient {
    private final RestTemplate restTemplate;
    private String statsServerUrl;

    public StatsClient(@Value("${services.stats-server.uri:http://localhost:9090}") String statsServerUrl) {
        this.restTemplate = new RestTemplate();
        this.statsServerUrl = statsServerUrl;
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

    public Collection<StatsDto> getStats(LocalDateTime start,
                                         LocalDateTime end,
                                         Collection<String> uris,
                                         boolean unique) {
        String url = UriComponentsBuilder.fromHttpUrl(statsServerUrl + "/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParamIfPresent("uris", Optional.of(uris))
                .queryParamIfPresent("unique", Optional.of(unique))
                .toUriString();
        ResponseEntity<Collection<StatsDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<StatsDto>>() {
                }
        );
        return response.getBody();
    }
}
