package com.example.demo.controllers;

import com.example.demo.domain.Event;
import com.example.demo.dto.EventDto;
import com.example.demo.mappers.EventMapper;
import com.example.demo.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllLists()
            .stream()
            .map(eventMapper::mapDomainToDto)
            .toList();
        return ResponseEntity.ok(events);
    }
}
