package ru.mirea.docker.elitetickets.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.docker.elitetickets.entities.EventEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {

    private String eventName;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private String eventPlace;

    private String eventMinAge;

    private boolean isActive;

    public static EventModel fromEntity(EventEntity event){
        return EventModel.builder()
                .eventName(event.getEventName())
                .eventDate(event.getEventDate())
                .eventTime(event.getEventTime())
                .eventPlace(event.getEventPlace())
                .eventMinAge(event.getEventMinAge())
                .isActive(event.isActive())
                .build();
    }
}
