package ru.mirea.docker.elitetickets.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReminderRequest {

    private String email;

    private String firstName;

    private String eventName;

    private String eventDate;

}
