package com.example.demo.mappers;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public User mapEntityToDomain(UserEntity eventEntity) {
        return User.builder()
            .id(eventEntity.getId())
            .name(eventEntity.getName())
            .createdAt(eventEntity.getCreatedAt())
            .build();
	}

    public User mapDtoToDomain(UserDto eventDto) {
        return User.builder()
            .id(eventDto.getId())
            .name(eventDto.getName())
            .build();
    }

    public UserDto mapDomainToDto(User event) {
        return UserDto.builder()
            .id(event.getId())
            .name(event.getName())
            .createdAt(event.getCreatedAt())
            .build();
    }
}
