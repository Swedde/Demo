package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.entities.EventEntity;
import com.example.demo.helpers.BaseTestWithDb;
import com.example.demo.repositories.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class UserServiceTest extends BaseTestWithDb {

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testCreateUser() {
        // GIVEN
        User user = User.builder()
            .name("Name")
            .build();

        // WHEN
        userService.createUserUpsert(user);

        // THEN
        List<EventEntity> eventEntityList = eventRepository.findAll();
        System.out.println(eventEntityList);
        Assertions.assertEquals(1, eventEntityList.size());
        Assertions.assertEquals(1, eventEntityList.getFirst().getCount());
    }
}