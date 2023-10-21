package ru.mirea.docker.elitetickets.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.docker.elitetickets.entities.OrderEntity;
import ru.mirea.docker.elitetickets.enums.PaymentStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private String orderId;

    private PaymentStatus paymentStatus;

    private TicketModel ticket;

    public static OrderModel fromEntity(OrderEntity entity){
        return OrderModel.builder()
                .orderId(entity.getId().toString())
                .paymentStatus(entity.getPaymentStatus())
                .ticket(TicketModel.fromEntity(entity.getTicket()))
                .build();
    }
}
