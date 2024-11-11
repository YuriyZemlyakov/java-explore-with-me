package ru.practicum.ewmstatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("ru.practicum.ewmDto")
public class StatApp {
    public static void main(String[] args) {
        SpringApplication.run(StatApp.class, args);
    }
}
