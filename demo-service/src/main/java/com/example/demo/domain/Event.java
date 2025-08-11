package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class Event {

    private Long id;

    private String type;

    private Long count;

    private OffsetDateTime createdAt;
}
