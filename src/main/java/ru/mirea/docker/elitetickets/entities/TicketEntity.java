package ru.mirea.docker.elitetickets.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import ru.mirea.docker.elitetickets.enums.TicketType;

import java.time.LocalDateTime;
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

    @Column
    private int price;

    @Column
    private LocalDateTime purchaseDate;

    @Column
    private boolean isRedeemed;
}
