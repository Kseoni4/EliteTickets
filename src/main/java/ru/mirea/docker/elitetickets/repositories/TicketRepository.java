package ru.mirea.docker.elitetickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.docker.elitetickets.entities.TicketEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    @Query("""
            SELECT ticket
            FROM TicketEntity ticket
            WHERE ticket.owner.email = :email
            """)
    List<TicketEntity> findTicketsByEmail(String email);
}
