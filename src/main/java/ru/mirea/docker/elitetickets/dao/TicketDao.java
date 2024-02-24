package ru.mirea.docker.elitetickets.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.docker.elitetickets.dto.models.TestDTO;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.repositories.TicketRepository;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TicketDao {

    private final TicketRepository repository;

    private final EntityManager entityManager;

    public TicketEntity createTicket(TicketEntity ticket){
        return repository.save(ticket);
    }

    public List<TicketModel> getAllTicketsByUser(String email){
        return repository.findTicketsByEmail(email).stream().map(TicketModel::fromEntity).toList();
    }

    public List<TicketEntity> getAllTicketEntitiesByUser(String email){
        return repository.findTicketsByEmail(email);
    }

    public List<TestDTO> getTicketId(String email){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketEntity> criteriaQuery = cb.createQuery(TicketEntity.class);
        Root<TicketEntity> ticket = criteriaQuery.from(TicketEntity.class);


        return repository.getTicketIdByEmail(email);
    }

    @Transactional
    public TicketModel redeemTicket(String ticketId){
        repository.redeemTicketById(UUID.fromString(ticketId));
        return TicketModel.fromEntity(repository.findById(UUID.fromString(ticketId)).orElseThrow());
    }
}
