package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.entities.EventEntity;
import com.example.demo.helpers.BaseTestWithDb;
import com.example.demo.repositories.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class UserServiceTest extends BaseTestWithDb {

//    @Autowired
    @MockitoSpyBean
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testCreateUser() {
        // GIVEN
        User user = User.builder()
            .name("Name")
            .build();
        Mockito.doReturn(user).when(userService).createUserUpsert(any());

        // WHEN
        userService.createUserUpsert(user);

        // THEN
        Mockito.verify(userService, Mockito.times(1)).createUserUpsert(any());

        List<EventEntity> eventEntityList = eventRepository.findAll();
        System.out.println(eventEntityList);
        Assertions.assertEquals(1, eventEntityList.size());
        Assertions.assertEquals(1, eventEntityList.getFirst().getCount());
    }
}