package com.example.demo.services;

import com.example.demo.domain.Event;
import com.example.demo.entities.EventEntity;
import com.example.demo.mappers.EventMapper;
import com.example.demo.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public List<Event> getAllLists() {
        return eventRepository.findAll()
            .stream()
            .map(eventMapper::mapEntityToDomain)
            .toList();
    }

    public Event createEvent(Event event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setType(event.getType());
        eventEntity = eventRepository.save(eventEntity);
        return eventMapper.mapEntityToDomain(eventEntity);
    }
}
