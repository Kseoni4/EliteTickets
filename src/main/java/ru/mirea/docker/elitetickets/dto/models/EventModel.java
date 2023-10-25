package ru.mirea.docker.elitetickets.dto.models;

import lombok.*;
import ru.mirea.docker.elitetickets.entities.EventEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {

    @Getter
    private String eventName;

    @Getter
    private LocalDate eventDate;

    @Getter
    @Setter
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
