package ru.mirea.docker.elitetickets.repositories;

import jakarta.persistence.Convert;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.QueryHint;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.HQLSelect;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Repository;
import ru.mirea.docker.elitetickets.dto.models.TestDTO;
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

    @Modifying
    @Query("""
            UPDATE TicketEntity ticket
            SET ticket.isRedeemed = true
            WHERE ticket.id = :id
        """)
    void redeemTicketById(UUID id);

    @Query("""
            SELECT ticket.id, ticket.price
            FROM TicketEntity ticket
            WHERE ticket.owner.email = :email
    """)
    List<TestDTO> getTicketIdByEmail(String email);
}
