package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.entities.UserEntity;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final EventService eventService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<User> getAllLists() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::mapEntityToDomain)
            .toList();
    }

    @Transactional
    public User createUserLockRows(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity = userRepository.save(userEntity);
        eventService.createEventLockRows("user_create_lock_rows");
        return userMapper.mapEntityToDomain(userEntity);
    }

    @Transactional
    public User createUserUpsert(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity = userRepository.save(userEntity);
        eventService.createEventUpsert("user_create_upsert");
        return userMapper.mapEntityToDomain(userEntity);
    }

    @Transactional
    public User createUserKafka(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity = userRepository.save(userEntity);
        eventService.createEventKafka("user_create_kafka");
        return userMapper.mapEntityToDomain(userEntity);
    }
}
