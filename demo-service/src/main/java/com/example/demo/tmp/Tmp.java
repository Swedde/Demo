package com.example.demo.tmp;

import com.example.demo.dto.UserDto;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Tmp {
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

        while (true) {
            executor.execute(action);
        }
    }
}
