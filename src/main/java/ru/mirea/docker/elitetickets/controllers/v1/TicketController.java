package ru.mirea.docker.elitetickets.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.docker.elitetickets.dao.TicketDao;
import ru.mirea.docker.elitetickets.dto.models.TestDTO;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.dto.requests.UserTicketRequest;
import ru.mirea.docker.elitetickets.dto.response.TicketRedeemResponse;
import ru.mirea.docker.elitetickets.dto.response.UserTicketsResponse;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.services.ticket.TicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController {


    private final TicketService ticketService;

    private final TicketDao ticketDao;

    @GetMapping("")
    public UserTicketsResponse getTicketsInfo(@RequestBody UserTicketRequest ticketRequest){
        List<TicketModel> ticketModelList = ticketService.getTicketsOfUser(ticketRequest);
        return new UserTicketsResponse(
                ticketRequest.getEmail(),
                ticketModelList
        );
    }

    @GetMapping("/{email}")
    public List<TestDTO> getTicketId(@PathVariable String email){
        return ticketDao.getTicketId(email);
    }

    @PostMapping("/redeem/{ticketId}")
    public TicketRedeemResponse redeemTicket(@PathVariable String ticketId){
        return ticketService.redeemTicket(ticketId);
    }

}
