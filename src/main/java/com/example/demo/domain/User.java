package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class User {

    private Long id;

    private String name;

    private OffsetDateTime createdAt;
}
