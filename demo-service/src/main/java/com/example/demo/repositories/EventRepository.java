package com.example.demo.repositories;

import com.example.demo.entities.EventEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EventEntity> findByType(String type);

    @Query(
        nativeQuery = true,
        value = """
            insert into events (type, count, created_at, updated_at)
                values (:type, 1, now(), now())
            on conflict (type)
            do update set count = events.count + 1
            returning *;
        """
    )
    EventEntity upsert(String type);

}
