package ru.mirea.docker.elitetickets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTicketsResponse {

    private String email;

    private List<TicketModel> tickets;

}
