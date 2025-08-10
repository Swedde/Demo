package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.mappers.UserMapper;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> events = userService.getAllLists()
            .stream()
            .map(userMapper::mapDomainToDto)
            .toList();
        return ResponseEntity.ok(events);
    }

    @PutMapping("/lock-rows")
    public ResponseEntity<UserDto> createUserLockRows(
        @RequestBody UserDto userDto
    ) {
        User user = userService.createUserLockRows(userMapper.mapDtoToDomain(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.mapDomainToDto(user));
    }

    @PutMapping("/upsert")
    public ResponseEntity<UserDto> createUserUpsert(
        @RequestBody UserDto userDto
    ) {
        User user = userService.createUserUpsert(userMapper.mapDtoToDomain(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.mapDomainToDto(user));
    }
}
