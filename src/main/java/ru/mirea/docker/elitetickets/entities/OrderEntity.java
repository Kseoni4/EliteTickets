package ru.mirea.docker.elitetickets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "ticket_orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private UserEntity owner;

    @OneToOne
    private TicketEntity ticket;

}
