package ru.mirea.docker.elitetickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.docker.elitetickets.entities.EventEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Optional<EventEntity> findByEventName(String eventName);

    @Query(
            """
              UPDATE EventEntity event
              SET event.eventDate = :eventDate,
                  event.eventTime = :eventTime
              WHERE event.eventName = :eventName
            """
    )
    void updateEventDateTime(LocalDate eventDate, LocalTime eventTime, String eventName);
}
