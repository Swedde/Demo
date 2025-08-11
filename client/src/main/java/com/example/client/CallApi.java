package com.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Builder
class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;
}


public class CallApi {
    private static final Duration duration = Duration.of(30, ChronoUnit.SECONDS);

    public static void main(String[] args) {

        var restClient = RestClient.create();

        var atomicInteger = new AtomicInteger();

//        var uri = "http://localhost:8080/users/lock-rows";
        var uri = "http://localhost:8080/users/upsert";

        Runnable action = () -> {
            restClient.put()
                .uri(uri)
                .body(UserDto.builder().name("Ivan").build())
                .retrieve()
                .body(String.class);
            System.out.println(atomicInteger.incrementAndGet());
        };

        ExecutorService executor = Executors.newFixedThreadPool(5);

        LocalDateTime start = LocalDateTime.now();
        while (true) {
            LocalDateTime now = LocalDateTime.now();
            System.out.printf("NOW = %s%n", now);
            System.out.printf("START = %s%n", start);
            if (start.plus(duration).isBefore(now)) {
                System.out.println("Stop");
                break;
            }
            executor.execute(action);
        }
    }
}