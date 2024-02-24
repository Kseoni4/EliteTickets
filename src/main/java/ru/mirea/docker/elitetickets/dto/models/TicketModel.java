package ru.mirea.docker.elitetickets.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.enums.TicketType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketModel {

    private UUID ticketId;

    private String eventName;

    private String ownerFirstName;

    private String ownerLastName;

    private int price;

    private TicketType ticketType;

    private LocalDateTime purchaseDate;

    private boolean isRedeemed;

    public static TicketModel fromEntity(TicketEntity ticket){
        return TicketModel.builder()
                .ticketId(ticket.getId())
                .eventName(ticket.getEvent().getEventName())
                .ownerFirstName(ticket.getOwner().getFirstName())
                .ownerLastName(ticket.getOwner().getLastName())
                .price(ticket.getPrice())
                .purchaseDate(ticket.getPurchaseDate())
                .isRedeemed(ticket.isRedeemed())
                .ticketType(ticket.getTicketType())
                .build();
    }
}
