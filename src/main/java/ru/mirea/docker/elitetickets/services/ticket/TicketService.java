package ru.mirea.docker.elitetickets.services.ticket;

import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.dto.requests.UserTicketRequest;
import ru.mirea.docker.elitetickets.dto.response.TicketRedeemResponse;

import java.awt.image.BufferedImage;
import java.util.List;

public interface TicketService {

    TicketRedeemResponse redeemTicket(String ticketId);

    BufferedImage getTicketImage(String ticketId);

    BufferedImage createTicketImage(TicketModel ticketModel);

    List<TicketModel> getTicketsOfUser(UserTicketRequest userTicketRequest);
}
