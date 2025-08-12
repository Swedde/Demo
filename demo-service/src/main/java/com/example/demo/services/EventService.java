package com.example.demo.services;

import com.example.demo.domain.Event;
import com.example.demo.entities.EventEntity;
import com.example.demo.kafka.EventKafkaProducer;
import com.example.demo.mappers.EventMapper;
import com.example.demo.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventKafkaProducer eventKafkaProducer;

    @Transactional(readOnly = true)
    public List<Event> getAllLists() {
        return eventRepository.findAll()
            .stream()
            .map(eventMapper::mapEntityToDomain)
            .toList();
    }

    @Transactional
    public Event createEventLockRows(String type) {
        Optional<EventEntity> eventEntityOpt = eventRepository.findByType(type);
        if (eventEntityOpt.isEmpty()) {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setType(type);
            eventEntity.setCount(1L);
            eventEntity = eventRepository.save(eventEntity);
            return eventMapper.mapEntityToDomain(eventEntity);
        }
        EventEntity eventEntity = eventEntityOpt.get();
        eventEntity.setCount(eventEntity.getCount() + 1);
        return eventMapper.mapEntityToDomain(eventEntity);
    }

    @Transactional
    public Event createEventUpsert(String type) {
        EventEntity eventEntity = eventRepository.upsert(type);
        return eventMapper.mapEntityToDomain(eventEntity);
    }

    public void createEventKafka(String type) {
        eventKafkaProducer.sendEvent(type);
    }
}
