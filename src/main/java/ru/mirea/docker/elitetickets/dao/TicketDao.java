package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.repositories.TicketRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketDao {

    private final TicketRepository repository;

    public TicketEntity createTicket(TicketEntity ticket){
        return repository.save(ticket);
    }

    public List<TicketModel> getAllTicketsByUser(String email){
        return repository.findTicketsByEmail(email).stream().map(TicketModel::fromEntity).toList();
    }

    public List<TicketEntity> getAllTicketEntitiesByUser(String email){
        return repository.findTicketsByEmail(email);
    }
}
