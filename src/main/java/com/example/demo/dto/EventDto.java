package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class EventDto {

	private Long id;

	private String type;

	private Long count;

    private OffsetDateTime createdAt;
}
