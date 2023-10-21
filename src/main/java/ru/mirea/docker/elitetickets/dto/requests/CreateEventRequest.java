package ru.mirea.docker.elitetickets.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {

    private String eventName;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private String eventPlace;

    private String eventMinAge;

}
