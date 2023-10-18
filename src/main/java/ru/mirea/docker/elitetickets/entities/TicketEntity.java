package ru.mirea.docker.elitetickets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import ru.mirea.docker.elitetickets.enums.TicketType;

import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private UserEntity owner;

    @ManyToOne
    private EventEntity event;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
}
