package ru.mirea.docker.elitetickets.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.entities.TicketEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatedOrderResponse {

    private String orderId;

    private String paymentStatus;

}
