package ru.mirea.docker.elitetickets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column
    private String eventName;

    @Column
    private LocalDate eventDate;

    @Column
    private LocalTime eventTime;

    @Column
    private String eventPlace;

    @Column
    private String eventMinAge;

    @Column
    private boolean isActive;

}
