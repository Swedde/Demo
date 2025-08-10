package com.example.demo.mappers;

import com.example.demo.domain.Event;
import com.example.demo.dto.EventDto;
import com.example.demo.entities.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
	public Event mapEntityToDomain(EventEntity eventEntity) {
        return Event.builder()
            .id(eventEntity.getId())
            .type(eventEntity.getType())
            .count(eventEntity.getCount())
            .createdAt(eventEntity.getCreatedAt())
            .build();
	}

    public Event mapDtoToDomain(EventDto eventDto) {
        return Event.builder()
            .id(eventDto.getId())
            .type(eventDto.getType())
            .build();
    }

    public EventDto mapDomainToDto(Event event) {
        return EventDto.builder()
            .id(event.getId())
            .type(event.getType())
            .count(event.getCount())
            .createdAt(event.getCreatedAt())
            .build();
    }
}
