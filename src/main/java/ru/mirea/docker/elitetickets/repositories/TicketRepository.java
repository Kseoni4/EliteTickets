package ru.mirea.docker.elitetickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.docker.elitetickets.entities.TicketEntity;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    @Query("""
            SELECT TicketEntity ticket
            FROM TicketEntity tickets
            WHERE tickets.owner.email = :email
            """)
    List<TicketEntity> findTicketsByEmail(String email);
}
