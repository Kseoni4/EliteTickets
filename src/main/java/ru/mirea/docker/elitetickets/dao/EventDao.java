package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.dto.requests.ChangeEventDateTimeRequest;
import ru.mirea.docker.elitetickets.entities.EventEntity;
import ru.mirea.docker.elitetickets.repositories.EventRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class EventDao {

    private final EventRepository repository;

    public EventEntity getEventFromName(String eventName){
        return repository.findByEventName(eventName).orElseThrow();
    }

    public void updateEventDateTime(ChangeEventDateTimeRequest request){
        repository.updateEventDateTime(request.getEventDate(), request.getEventTime(), request.getEventName());
    }
}
